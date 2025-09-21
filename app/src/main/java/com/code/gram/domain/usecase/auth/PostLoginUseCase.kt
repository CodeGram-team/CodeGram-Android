package com.code.gram.domain.usecase.auth

import com.code.gram.domain.repository.AuthRepository
import javax.inject.Inject

class PostLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String) = authRepository.login(idToken)
}