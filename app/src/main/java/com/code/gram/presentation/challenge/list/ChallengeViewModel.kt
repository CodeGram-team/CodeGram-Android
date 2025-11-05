package com.code.gram.presentation.challenge.list

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.domain.repository.ChallengeRepository
import com.code.gram.presentation.challenge.model.DifficultyType
import com.code.gram.presentation.challenge.list.model.toUiModel
import com.code.gram.presentation.challenge.list.state.ChallengeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val _state : MutableStateFlow<ChallengeState> = MutableStateFlow(ChallengeState())
    val state : StateFlow<ChallengeState> = _state.asStateFlow()

    init {
        fetchChallengeList(
            difficulty = DifficultyType.INTRODUCTORY
        )

        viewModelScope.launch {
            _state
                .map { it.query }
                .distinctUntilChanged()
                .debounce(300L)
                .collect { query ->
                    _state.update { currentState ->

                        val filteredList = if (query.isBlank()) {
                            currentState.sourceChallengeList
                        } else {
                            currentState.sourceChallengeList.filter {
                                it.title.contains(query, ignoreCase = true)
                            }.toImmutableList()
                        }

                        currentState.copy(
                            challengeList = filteredList
                        )
                    }
                }
        }
    }

    fun updateSearchQuery(query : String) {
        _state.update {
            it.copy(query = query)
        }
    }

    fun fetchChallengeList(difficulty: DifficultyType) {
        viewModelScope.launch {
            challengeRepository.getDifficultyChallenges(
                difficulty = difficulty.name.lowercase(),
                page = 1,
                size = 10
            ).onSuccess { result ->
                _state.update { currentState ->
                    currentState.copy(
                        challengeList = result.map { it.toUiModel() }.toImmutableList(),
                        sourceChallengeList = result.map { it.toUiModel() }.toImmutableList()
                    )
                }
            }.onFailure {
                Timber.e("챌린지 호출 실패: ${it.message}")
            }
        }
    }
}