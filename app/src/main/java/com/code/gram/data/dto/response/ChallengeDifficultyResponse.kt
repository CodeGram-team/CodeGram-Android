package com.code.gram.data.dto.response

import com.code.gram.domain.entity.challenge.ChallengeDifficultyEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeDifficultyResponse(
    @SerialName("title")
    val title : String,
    @SerialName("problem_id")
    val problemId : Int,
    @SerialName("difficulty")
    val difficulty : String
) {
    fun toDomain() : ChallengeDifficultyEntity {
        return ChallengeDifficultyEntity(
            title = title,
            problemId = problemId,
            difficulty = difficulty
        )
    }
}