package com.code.gram.data.service

import com.code.gram.data.dto.response.AuthResponse
import com.code.gram.data.dto.request.LoginRequestDto
import com.code.gram.data.dto.request.RefreshRequest
import com.code.gram.data.dto.request.SignUpRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("api/v1/google/auth")
    suspend fun authLogin(
        @Body idToken: LoginRequestDto,
    ) : Response<AuthResponse>


    @POST("api/v1/google/signup")
    suspend fun signUp(
        @Body body: SignUpRequestDto,
    ) : Response<AuthResponse>

    @POST("api/v1/refresh")
    suspend fun refreshToken(
        @Body refreshToken: RefreshRequest,
    ) : Response<AuthResponse>
}