package com.code.gram.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.core.model.FeedModel
import com.code.gram.core.model.toUiModel
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.domain.repository.WebSocketRepository
import com.code.gram.presentation.home.model.SortType
import com.example.makersassignment.core.common.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val webSocketRepository: WebSocketRepository
) : ViewModel() {
    private val _state : MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state.asStateFlow()

    /*init {
        fetchData()
    }
*/
    fun fetchData() {
        if (_state.value.isLoading) return

        viewModelScope.launch {
            val page = _state.value.page
            val sortType = _state.value.sortType.name.lowercase()

            homeRepository.getPosts(page,sortType)
                .onSuccess { result ->
                    _state.update { currentState ->
                        val newItems = result.map { it.toUiModel() }.toImmutableList()

                        val mergedItems = if (page == 1) {
                            newItems
                        } else {
                            when (val currentFeed = currentState.feedItem) {
                                is UiState.Success -> (currentFeed.data + newItems).toImmutableList()
                                else -> newItems
                            }
                        }

                        currentState.copy(
                            feedItem = UiState.Success(mergedItems),
                            isLoading = false
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e.message.toString())
                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
        }
    }

    fun updatePage() {
        _state.update {
            it.copy(
                page = if (it.page < 2) {
                    it.page + 1
                } else {
                    it.page
                }
            )
        }
    }

    fun updateSortType(sortType: SortType) {
        _state.update {
            it.copy(
                sortType = sortType,
                page = 1,
                feedItem = UiState.Loading,
            )
        }

        fetchData()
    }
    fun fetchFavorite(
        postId: String
    ) {
        viewModelScope.launch {
            homeRepository.postLike(postId)
                .onSuccess { result ->
                    _state.update { currentState ->
                        val currentFeedState = currentState.feedItem
                        if (currentFeedState is UiState.Success) {

                            val currentList = currentFeedState.data

                            val updatedList = currentList.map { feedModel ->
                                if (feedModel.id == postId) {
                                    feedModel.copy(
                                        isFavorite = result.userHasLiked,
                                        likesCount = result.likeCount
                                    )
                                } else {
                                    feedModel
                                }
                            }

                            currentState.copy(
                                feedItem = UiState.Success(updatedList)
                            )
                        } else {
                            currentState
                        }
                    }
                }
                .onFailure {
                    Timber.e(it.message.toString())
                }
        }
    }

    fun sendComment(
        content: String,
        postId: String
    ) {
        viewModelScope.launch {
            homeRepository.postComment(postId, content)
                .onSuccess { result ->
                    Timber.e("sendComment success: ${result.comments}")

                    _state.update { currentState ->
                        val currentFeedState = currentState.feedItem

                        if (currentFeedState is UiState.Success) {
                            val currentList = currentFeedState.data

                            val updatedList = currentList.map { feedModel ->
                                if (feedModel.id == postId) {
                                    result.toUiModel()
                                } else {
                                    feedModel
                                }
                            }

                            currentState.copy(
                                feedItem = UiState.Success(updatedList)
                            )
                        } else {
                            currentState
                        }
                    }
                }
                .onFailure {
                    Timber.e(it.message.toString())
                }
        }
    }



    fun startJob(feedItem: FeedModel) {
        _state.update {
            it.copy(
                codeResult = ""
            )
        }
        viewModelScope.launch {
            webSocketRepository.startJob(
                language = feedItem.language,
                code = feedItem.code
            ).onSuccess { result ->
                Timber.e("success ${result}")
                _state.update {
                    it.copy(
                        isSuccess = true
                    )
                }
                observeMessages()
            }.onFailure {
                Timber.e("fail ${it}")
            }
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            webSocketRepository.observeJobResult().collect { result ->
                _state.update {
                    it.copy(
                        codeResult = it.codeResult + "\n[${result.type}] ${result.data}"
                    )
                }
                Timber.e("result ${result}")
            }
        }
    }

    fun sendInput() {
        val input = _state.value.userInput
        webSocketRepository.sendInput(input)
        _state.update {
            it.copy(
                userInput = ""
            )
        }
    }

    override fun onCleared() {
        webSocketRepository.disconnect()
        super.onCleared()
    }

    fun onInputChanged(input: String) {
        _state.update {
            it.copy(
                userInput = input
            )
        }
    }
}