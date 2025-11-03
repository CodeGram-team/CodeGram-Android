package com.code.gram.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChallengeRequest(
    @SerialName("language")
    val language: String,
    @SerialName("code")
    val code: String,
)
