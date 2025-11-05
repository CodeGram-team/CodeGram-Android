package com.code.gram.data.service

import com.code.gram.data.dto.response.CodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("api/v1/search")
    suspend fun postSearch(
        @Query("vibe_emoji") vibeEmojis: List<String>? = null,
        @Query("language") language: String? = null,
        @Query("tag") tags: List<String>? = null,
        @Query("q") query: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
    ): Response<List<CodeResponse>>
}
