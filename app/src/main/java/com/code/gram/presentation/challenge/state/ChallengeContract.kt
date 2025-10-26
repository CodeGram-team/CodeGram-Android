package com.code.gram.presentation.challenge.state

import androidx.compose.runtime.Immutable
import com.code.gram.presentation.challenge.model.ChallengeItemModel

@Immutable
data class ChallengeState(
    val challengeList : List<ChallengeItemModel> = emptyList(),
    val query : String = "",
)