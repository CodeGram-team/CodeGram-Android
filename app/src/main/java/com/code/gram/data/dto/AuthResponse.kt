package com.code.gram.data.dto

import com.code.gram.domain.entity.LoginEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("status")
    val status: String = "",
    @SerialName("access_token")
    val accessToken: String = "",
    @SerialName("refresh_token")
    val refreshToken: String = "",
    @SerialName("signup_token")
    val signupToken: String = "",
    @SerialName("token_type")
    val tokenType: String
) {
    fun toDomain(): LoginEntity {
        return LoginEntity(status, accessToken, refreshToken, signupToken, tokenType)
    }
}
