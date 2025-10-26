package com.code.gram.data.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto (
    @SerialName("id_token")
    val idToken : String
)