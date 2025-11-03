package com.code.gram.data.datasourceimpl

import com.code.gram.data.datasource.AuthRemoteDataSource
import com.code.gram.data.dto.response.AuthResponse
import com.code.gram.data.dto.request.LoginRequestDto
import com.code.gram.data.dto.request.RefreshRequest
import com.code.gram.data.dto.request.SignUpRequestDto
import com.code.gram.data.service.LoginService
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : AuthRemoteDataSource {
    override suspend fun authLogin(loginRequestDto: LoginRequestDto): Response<AuthResponse> =
        loginService.authLogin(loginRequestDto)

    override suspend fun signUp(body: SignUpRequestDto): Response<AuthResponse> =
        loginService.signUp(body)

    override suspend fun refreshToken(refreshToken: RefreshRequest): Response<AuthResponse> =
        loginService.refreshToken(refreshToken)
}
