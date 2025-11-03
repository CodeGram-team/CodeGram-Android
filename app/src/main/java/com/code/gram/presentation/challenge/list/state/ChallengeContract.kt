package com.code.gram.presentation.challenge.list.state

import androidx.compose.runtime.Immutable
import com.code.gram.presentation.challenge.list.model.ChallengeItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class ChallengeState(
    val challengeList : ImmutableList<ChallengeItemModel> = persistentListOf(),
    val query : String = "",
)