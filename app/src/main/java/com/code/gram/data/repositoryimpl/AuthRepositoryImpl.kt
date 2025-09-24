package com.code.gram.data.repositoryimpl

import android.content.Context
import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.AuthRemoteDataSource
import com.code.gram.data.datasource.GoogleAuthDataSource
import com.code.gram.data.datasource.TokenManager
import com.code.gram.data.dto.LoginRequestDto
import com.code.gram.data.dto.SignUpRequestDto
import com.code.gram.domain.entity.LoginEntity
import com.code.gram.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
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
        val loginResponse = authRemoteDataSource.authLogin(login)
        if (loginResponse.isSuccessful) {
            val loginEntity = loginResponse.body()?.toDomain() ?: throw Exception("Response body is null")
            if (loginEntity.status == "signup") {
                Timber.e("signupImpl, $loginEntity")
                tokenManager.saveAccessToken(loginEntity.accessToken)
                tokenManager.saveRefreshToken(loginEntity.refreshToken)
                tokenManager.saveSignUpToken(loginEntity.signupToken)
                loginEntity
            } else {
                Timber.e("signupImplfail, $loginEntity")
                tokenManager.saveAccessToken(loginEntity.accessToken)
                tokenManager.saveRefreshToken(loginEntity.refreshToken)
                tokenManager.saveSignUpToken(loginEntity.signupToken)
                loginEntity
            }
        } else {
            throw Exception("Login failed: ${loginResponse.errorBody()?.string()}")
        }
    }

    override suspend fun serverLogin(signUpToken: String, nickname: String): Result<LoginEntity> {
        val body = SignUpRequestDto(signUpToken, nickname)
        val response = authRemoteDataSource.signUp(body)
        if (response.isSuccessful) {
            val loginEntity = response.body()?.toDomain() ?: throw Exception("Response body is null")
            tokenManager.saveAccessToken(loginEntity.accessToken)
            tokenManager.saveRefreshToken(loginEntity.refreshToken)
            return Result.success(loginEntity)
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
    }

    override fun getSignUpToken(): Flow<String?> {
        return tokenManager.getSignUpToken()
    }
}
