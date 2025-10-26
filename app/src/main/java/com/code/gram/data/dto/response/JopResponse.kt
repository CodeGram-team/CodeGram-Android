package com.code.gram.data.dto.response

import com.code.gram.domain.entity.websocket.WebSocketEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JopResponse(
    @SerialName("job_id")
    val jobId: String,
    @SerialName("websocket_url")
    val webSocketUrl: String
) {
    fun toDomain() = WebSocketEntity(
        jobId = jobId,
        webSocketUrl = webSocketUrl
    )
}