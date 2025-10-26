package com.code.gram.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.presentation.home.model.FeedModel
import com.code.gram.presentation.home.model.toUiModel
import com.example.makersassignment.core.common.util.UiState
import com.wakaztahir.codeeditor.highlight.model.CodeLang
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
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _state : MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state.asStateFlow()

    init {
        initFetchData()
    }

    fun initFetchData() {
        viewModelScope.launch {
            homeRepository.getPosts()
                .onSuccess { result ->
                    _state.update { currentState ->
                        currentState.copy(
                            feedItem = UiState.Success(result.map { it.toUiModel() }.toImmutableList())
                        )
                    }
                    Timber.e(result.toString())
                }
                .onFailure { e ->
                    Timber.e(e.message.toString())
                }
        }
    }
}