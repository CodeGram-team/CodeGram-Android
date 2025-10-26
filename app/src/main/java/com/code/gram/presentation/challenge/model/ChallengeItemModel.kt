package com.code.gram.presentation.challenge.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChallengeItemModel (
    val title : String = "",
    val description : String = "",
    val level : ChallengeLevel = ChallengeLevel.Easy,
    val type : String = "",
)

enum class ChallengeLevel(
    val displayName: String
) {
    All("전체"),
    Easy("Easy"),
    Medium("Medium"),
    Hard("Hard");

    companion object {
        fun fromDisplayName(displayName: String): ChallengeLevel {
            return entries.find { it.displayName == displayName } ?: All
        }
    }
}