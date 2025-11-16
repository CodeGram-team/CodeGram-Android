package com.code.gram.presentation.challenge.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.code.gram.domain.repository.ChallengeRepository
import com.code.gram.presentation.challenge.detail.model.toUiModel
import com.code.gram.presentation.challenge.detail.navigation.ChallengeDetail
import com.code.gram.presentation.challenge.detail.state.ChallengeDetailState
import com.code.gram.presentation.challenge.list.state.ChallengeState
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val challengeRepository: ChallengeRepository
) : ViewModel() {
    private val _state : MutableStateFlow<ChallengeDetailState> = MutableStateFlow(ChallengeDetailState())
    val state : StateFlow<ChallengeDetailState> = _state.asStateFlow()

    val problemId = savedStateHandle.toRoute<ChallengeDetail>()

    init {
        fetchChallengeDetail()
    }

    fun fetchChallengeDetail() {
        viewModelScope.launch {
            challengeRepository.getChallenge(
                problemId = problemId.challengeId
            ).onSuccess { result ->
                _state.update { currentState ->
                    currentState.copy(
                        problemDetailUiModel = result.toUiModel()
                    )
                }
            }.onFailure {
                Timber.e("챌린지 호출 실패: ${it.message}")
            }
        }
    }

    fun postChallenge() {
        viewModelScope.launch {
            challengeRepository.postChallenge(
                problemId = problemId.challengeId,
                language = _state.value.language,
                code = _state.value.code
            ).onSuccess { result ->
                _state.update { currentState ->
                    currentState.copy(
                        challengeResult = result.result.toUiModel()
                    )
                }
            }.onFailure {
                Timber.e("챌린지 post 실패: ${it.message}")
            }
        }
    }

    fun updateCode(
        code : String,
        isComplete: Boolean = false
    ) {
        if (!isComplete) {
            _state.update { currentState ->
                currentState.copy(
                    code = code
                )
            }
        } else {
            postChallenge()
        }
    }

    fun updateLanguage(language : CodeLang) {
        Timber.e("language ${language.name}")
        _state.update {
            it.copy(
                language = if (language.name == "JavaScript") {
                    "NodeJs".lowercase()
                } else {
                    language.name.lowercase()
                }
            )
        }
    }
}