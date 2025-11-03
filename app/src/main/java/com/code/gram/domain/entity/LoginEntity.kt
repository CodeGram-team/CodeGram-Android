package com.code.gram.domain.entity

data class LoginEntity(
    val status: String = "",
    val accessToken: String,
    val refreshToken: String,
    val signupToken: String,
    val expiresTime: Long,
    val tokenType: String
)