package com.code.gram.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto (
    @SerialName("signup_token")
    val signUpToken : String,
    @SerialName("nickname")
    val nickname : String
)