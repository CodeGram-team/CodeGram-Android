package com.code.gram.data.dto.response

import com.code.gram.domain.entity.challenge.ChallengeProblemEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeProblemResponse(
    @SerialName("problem_id")
    val problemId : Int,
    @SerialName("difficulty")
    val difficulty : String,
    @SerialName("question")
    val question : String,
    @SerialName("starter_code")
    val starterCode: String,
    @SerialName("url")
    val url: String
) {
    fun toDomain() : ChallengeProblemEntity {
        return ChallengeProblemEntity(
            problemId = problemId,
            difficulty = difficulty,
            question = question,
            starterCode = starterCode,
            url = url
        )
    }
}
