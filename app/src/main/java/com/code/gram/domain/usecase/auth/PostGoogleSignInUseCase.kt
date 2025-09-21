package com.code.gram.domain.usecase.auth

import android.content.Context
import com.code.gram.domain.repository.AuthRepository
import javax.inject.Inject

class PostGoogleSignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(context: Context) = authRepository.signInWithGoogle(context)
}