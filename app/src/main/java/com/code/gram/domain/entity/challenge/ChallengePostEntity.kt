package com.code.gram.domain.entity.challenge

data class ChallengePostEntity(
    val submissionId: String,
    val result : ChallengeResultEntity
)

data class ChallengeResultEntity(
    val status: String,
    val failedCase: Int?,
    val executionTime: Double?,
    val message: String?,
)
