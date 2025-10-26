package com.code.gram.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class WebSocketMessage(
    val type: String,
    val data: String
)