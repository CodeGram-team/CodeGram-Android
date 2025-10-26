package com.code.gram.data.datasource.websocket

import com.code.gram.data.dto.request.JopRequest
import com.code.gram.data.service.WebSocketService
import javax.inject.Inject

class WebSocketDataSource @Inject constructor(
    private val webSocketService: WebSocketService
) {
    suspend fun startJob(language: String, code: String) = webSocketService.startJob(
        request = JopRequest(
            language,
            code
        )
    )
}