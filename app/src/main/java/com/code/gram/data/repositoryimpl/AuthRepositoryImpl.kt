package com.code.gram.data.repositoryimpl

import android.content.Context
import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.AuthRemoteDataSource
import com.code.gram.data.datasource.GoogleAuthDataSource
import com.code.gram.data.datasource.TokenManager
import com.code.gram.data.dto.LoginRequestDto
import com.code.gram.domain.entity.LoginEntity
import com.code.gram.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val googleAuthDataSource: GoogleAuthDataSource,
    private val tokenManager: TokenManager,
) : AuthRepository {
    override suspend fun signInWithGoogle(context: Context): Result<String> =
        googleAuthDataSource.signIn(context).map { it.idToken }
        
    override suspend fun login(idToken: String): Result<LoginEntity> = suspendRunCatching {
        val login = LoginRequestDto(idToken)
        val loginResponse = authRemoteDataSource.login(login)
        if (loginResponse.isSuccessful) {
            val loginEntity = loginResponse.body()?.toDomain() ?: throw Exception("Response body is null")
            if (loginEntity.accessToken == "") {
                tokenManager.saveAccessToken(loginEntity.signupToken)
                loginEntity
            } else {
                tokenManager.saveAccessToken(loginEntity.accessToken)
                tokenManager.saveRefreshToken(loginEntity.refreshToken)
                loginEntity
            }
        } else {
            throw Exception("Login failed: ${loginResponse.errorBody()?.string()}")
        }
    }    
}
