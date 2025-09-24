package com.code.gram.data.datasource

import com.code.gram.data.dto.AuthResponse
import com.code.gram.data.dto.LoginRequestDto
import com.code.gram.data.dto.SignUpRequestDto
import retrofit2.Response

// 받은 idToken을 토대로 서버에 넘길 때
interface AuthRemoteDataSource {
    suspend fun authLogin(loginRequestDto: LoginRequestDto): Response<AuthResponse>
    suspend fun signUp(body: SignUpRequestDto) : Response<AuthResponse>
}