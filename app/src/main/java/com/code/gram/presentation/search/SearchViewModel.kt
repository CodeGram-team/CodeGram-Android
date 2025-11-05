package com.code.gram.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.core.model.FeedModel
import com.code.gram.core.model.toUiModel
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.domain.repository.SearchRepository
import com.code.gram.domain.repository.WebSocketRepository
import com.code.gram.presentation.search.model.SearchQueryUiModel
import com.code.gram.presentation.search.state.SearchState
import com.example.makersassignment.core.common.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository,
    private val webSocketRepository: WebSocketRepository,
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun updateSearchQuery(searchQueryUiModel: SearchQueryUiModel) {
        Timber.e("updateSearchQuery ${searchQueryUiModel}")
        _state.update {
            it.copy(
                searchQueryUiModel = it.searchQueryUiModel.copy(
                    vibeEmojis = searchQueryUiModel.vibeEmojis,
                    query = searchQueryUiModel.query,
                    language = searchQueryUiModel.language,
                    tags = searchQueryUiModel.tags,
                    page = searchQueryUiModel.page,
                    size = searchQueryUiModel.size
                )
            )
        }
    }

    fun fetchFavorite(
        postId: String
    ) {
        viewModelScope.launch {
            homeRepository.postLike(postId)
                .onSuccess { result ->
                    _state.update {
                        it.copy(
                            isFavorite = result.userHasLiked
                        )
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
                    _state.update {
                        it.copy(
                            searchResultList = it.searchResultList.map {
                                if (it.id == postId) {
                                    result.toUiModel()

                                } else {
                                    it
                                }
                            }
                        )
                    }
                }
                .onFailure {
                    Timber.e(it.message.toString())
                }
        }
    }

    fun fetchSearch() {
        val queryState = _state.value.searchQueryUiModel
        Timber.e("fetchSearch ${queryState}")
        viewModelScope.launch {
            repository.postSearch(
                vibeEmojis = queryState.vibeEmojis,
                language = queryState.language,
                tags = queryState.tags,
                query = queryState.query,
                page = queryState.page,
                size = queryState.size
            ).onSuccess { result ->
                _state.update { currentState ->
                    currentState.copy(
                        searchResultList = result.map { it.toUiModel() }
                    )
                }
            }.onFailure {
                Timber.e(it.message.toString())
            }
        }
    }

    fun startJob(feedItem: FeedModel) {
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