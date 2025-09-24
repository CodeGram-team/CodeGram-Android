package com.code.gram.domain.repository

import android.content.Context
import com.code.gram.domain.entity.LoginEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(context: Context): Result<String>
    suspend fun login(idToken: String): Result<LoginEntity>

    suspend fun serverLogin(signUpToken: String, nickname: String): Result<LoginEntity>
    fun getSignUpToken(): Flow<String?>
}