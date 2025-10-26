package com.code.gram.data.repositoryimpl

import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.websocket.WebSocketDataSource
import com.code.gram.data.datasource.websocket.WebSocketManager
import com.code.gram.data.dto.response.WebSocketMessage
import com.code.gram.domain.entity.websocket.JobResult
import com.code.gram.domain.entity.websocket.WebSocketEntity
import com.code.gram.domain.repository.WebSocketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

class WebSocketRepositoryImpl @Inject constructor(
    private val webSocket: WebSocketDataSource,
    private val webSocketManager: WebSocketManager
) : WebSocketRepository {
    override suspend fun startJob(
        language: String,
        code: String
    ): Result<WebSocketEntity> = suspendRunCatching {
        val response = webSocket.startJob(language, code)
        if (response.isSuccessful) {
            val webSocketEntity = response.body()?.toDomain() ?: throw Exception("Response body is null")
            webSocketManager.connect(webSocketEntity.webSocketUrl)
            webSocketEntity
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
    }

    override fun observeJobResult(): Flow<JobResult> {
        return webSocketManager.messages.map { json ->
            Json.decodeFromString(WebSocketMessage.serializer(), json)
        }.map { msg ->
            JobResult(type = msg.type, data = msg.data)
        }
    }

    override fun sendInput(input: String) {
        webSocketManager.send(input)
    }

    override fun disconnect() {
        webSocketManager.close()
    }
}