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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

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
                        challengeList = result.map { it.toUiModel() }.toImmutableList()
                    )
                }
            }.onFailure {
                Timber.e("챌린지 호출 실패: ${it.message}")
            }
        }
    }
}