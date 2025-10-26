package com.code.gram.domain.usecase.websocket

import com.code.gram.domain.entity.websocket.WebSocketEntity
import com.code.gram.domain.repository.WebSocketRepository

class StartJobUseCase(
    private val repository: WebSocketRepository
) {
    suspend operator fun invoke(
        language: String,
        code: String
    ): Result<WebSocketEntity> {
        return repository.startJob(
            language = language,
            code = code
        )
    }
}