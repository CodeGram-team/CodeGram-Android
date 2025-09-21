package com.code.gram.domain.repository

import android.content.Context
import com.code.gram.domain.entity.LoginEntity

interface AuthRepository {
    suspend fun signInWithGoogle(context: Context): Result<String>
    suspend fun login(idToken: String): Result<LoginEntity>
}