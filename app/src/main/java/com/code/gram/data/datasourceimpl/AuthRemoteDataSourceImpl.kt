package com.code.gram.data.datasourceimpl

import com.code.gram.data.datasource.AuthRemoteDataSource
import com.code.gram.data.dto.AuthResponse
import com.code.gram.data.dto.LoginRequestDto
import com.code.gram.data.dto.SignUpRequestDto
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
}
