package com.code.gram.presentation.challenge

import androidx.lifecycle.ViewModel
import com.code.gram.presentation.challenge.model.ChallengeItemModel
import com.code.gram.presentation.challenge.model.ChallengeLevel
import com.code.gram.presentation.challenge.state.ChallengeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(

) : ViewModel() {
    private val _state : MutableStateFlow<ChallengeState> = MutableStateFlow(ChallengeState())
    val state : StateFlow<ChallengeState> = _state.asStateFlow()

    private val dummyList = listOf(
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Easy,
            type = "Array"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Easy,
            type = "Array"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Easy,
            type = "Array"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Easy,
            type = "Array"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Medium,
            type = "DP"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Medium,
            type = "DP"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Medium,
            type = "DP"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Hard,
            type = "Tree"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Hard,
            type = "Tree"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Hard,
            type = "Tree"
        ),
        ChallengeItemModel(
            title = "코딩테스트",
            description = "코딩테스트를 해볼까요?",
            level = ChallengeLevel.Hard,
            type = "Tree"
        )
    )

    init {
        fetchChallengeList()
    }

    fun updateSearchQuery(query : String) {
        _state.update {
            it.copy(query = query)
        }
    }

    fun fetchChallengeList() {
        _state.update {
            it.copy(challengeList = dummyList)
        }
    }
}