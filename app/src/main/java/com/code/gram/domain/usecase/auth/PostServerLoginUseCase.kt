package com.code.gram.domain.usecase.auth

import com.code.gram.domain.repository.AuthRepository
import javax.inject.Inject

class PostServerLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        signUpToken: String,
        nickname: String
    ) = authRepository.serverLogin(
        signUpToken,
        nickname
    )
}