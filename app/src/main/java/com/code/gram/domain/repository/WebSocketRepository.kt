package com.code.gram.domain.repository

import com.code.gram.domain.entity.websocket.JobResult
import com.code.gram.domain.entity.websocket.WebSocketEntity
import kotlinx.coroutines.flow.Flow

interface WebSocketRepository {
    suspend fun startJob(
        language: String,
        code: String
    ): Result<WebSocketEntity>

    fun observeJobResult(): Flow<JobResult>
    fun sendInput(input: String)
    fun disconnect()
}