package com.code.gram.presentation.challenge.detail.model

import androidx.compose.ui.graphics.Color
import com.code.gram.core.designsystem.theme.Error
import com.code.gram.core.designsystem.theme.ErrorDark
import com.code.gram.core.designsystem.theme.Success
import com.code.gram.core.designsystem.theme.SuccessDark
import com.code.gram.core.designsystem.theme.Warning
import com.code.gram.core.designsystem.theme.WarningDark
import com.code.gram.domain.entity.challenge.ChallengeProblemEntity
import com.code.gram.presentation.challenge.model.DifficultyType

data class ProblemDetailUiModel(
    val problemId : Int,
    val difficulty : DifficultyType,
    val question: String,
    val starterCode: String,
    val url: String,
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

fun ChallengeProblemEntity.toUiModel() = ProblemDetailUiModel(
    problemId = problemId,
    difficulty = DifficultyType.fromLabel(difficulty),
    question = question,
    starterCode = starterCode,
    url = url
)