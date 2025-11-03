package com.code.gram.presentation.challenge.detail.model

import com.code.gram.domain.entity.challenge.ChallengeResultEntity

data class ChallengeResultUiModel(
    val status: String,
    val failedCase: Int?,
    val executionTime: Double?,
    val message: String?,
)

fun ChallengeResultEntity.toUiModel() = ChallengeResultUiModel(
    status = status,
    failedCase = failedCase,
    executionTime = executionTime,
    message = message
)
