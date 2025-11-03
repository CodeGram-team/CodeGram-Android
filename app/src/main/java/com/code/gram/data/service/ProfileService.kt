package com.code.gram.data.service

import com.code.gram.data.dto.response.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileService {
    @GET("api/v1/profile/me")
    suspend fun getUserInfo(): Response<UserInfoResponse>

    @GET("api/v1/profile/{nickname}")
    suspend fun getUserInfoByNickname(
        @Path("nickname") nickname: String
    ): Response<UserInfoResponse>
}