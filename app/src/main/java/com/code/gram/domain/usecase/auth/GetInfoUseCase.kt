package com.code.gram.domain.usecase.auth

import com.code.gram.domain.repository.AuthRepository
import javax.inject.Inject

class GetInfoUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getSignUpToken()
}