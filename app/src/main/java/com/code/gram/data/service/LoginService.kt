package com.code.gram.data.service

import com.code.gram.data.dto.AuthResponse
import com.code.gram.data.dto.LoginRequestDto
import com.code.gram.data.dto.SignUpRequestDto
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

}