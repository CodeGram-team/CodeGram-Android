package com.code.gram.data.service

import com.code.gram.data.dto.AuthResponse
import com.code.gram.data.dto.LoginRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/google/auth")
    suspend fun login(
        @Body idToken: LoginRequestDto,
    ) : Response<AuthResponse>
}