package com.code.gram.presentation.challenge.list.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.code.gram.core.designsystem.theme.Error
import com.code.gram.core.designsystem.theme.ErrorDark
import com.code.gram.core.designsystem.theme.Success
import com.code.gram.core.designsystem.theme.SuccessDark
import com.code.gram.core.designsystem.theme.Warning
import com.code.gram.core.designsystem.theme.WarningDark
import com.code.gram.domain.entity.challenge.ChallengeDifficultyEntity
import com.code.gram.presentation.challenge.model.DifficultyType

@Immutable
data class ChallengeItemModel (
    val title: String,
    val problemId: Int,
    val difficulty: DifficultyType,
) {
    val color: Color
        get() = when(difficulty) {
            DifficultyType.INTRODUCTORY -> Success
            DifficultyType.COMPETITION -> Warning
            DifficultyType.INTERVIEW -> Error
        }

    val levelBackgroundColor : Color
        get() = when(difficulty) {
            DifficultyType.INTRODUCTORY -> SuccessDark
            DifficultyType.COMPETITION -> WarningDark
            DifficultyType.INTERVIEW -> ErrorDark
        }
}

fun ChallengeDifficultyEntity.toUiModel() = ChallengeItemModel(
    title = title,
    problemId = problemId,
    difficulty = DifficultyType.fromLabel(difficulty)
)