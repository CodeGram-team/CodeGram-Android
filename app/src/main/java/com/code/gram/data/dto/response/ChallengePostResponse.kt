package com.code.gram.data.dto.response

import com.code.gram.domain.entity.challenge.ChallengePostEntity
import com.code.gram.domain.entity.challenge.ChallengeResultEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengePostResponse(
    @SerialName("submission_id")
    val submissionId: String,
    @SerialName("result")
    val result: ChallengeResultResponse
) {
    fun toDomain(): ChallengePostEntity {
        return ChallengePostEntity(
            submissionId = submissionId,
            result = result.toDomain()
        )
    }
}

@Serializable
data class ChallengeResultResponse(
    @SerialName("status")
    val status: String,
    @SerialName("failed_case")
    val failedCase: Int?,
    @SerialName("execution_time")
    val executionTime: Double?,
    @SerialName("message")
    val message: String?
) {
    fun toDomain(): ChallengeResultEntity {
        return ChallengeResultEntity(
            status = status,
            failedCase = failedCase,
            executionTime = executionTime,
            message = message
        )
    }
}
