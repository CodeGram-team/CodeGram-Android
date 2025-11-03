package com.code.gram.data.service

import com.code.gram.data.dto.request.CodeRequest
import com.code.gram.data.dto.request.CommentRequest
import com.code.gram.data.dto.response.CodeResponse
import com.code.gram.data.dto.response.LikeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeService {
    @GET("api/v1/feeds")
    suspend fun getCodeList(
        @Query("sort_by") sortBy: String = "latest",
        @Query("language") language: String? = null,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10
    ): Response<List<CodeResponse>>

    @POST("api/v1/posts")
    suspend fun postCode(
        @Body codeRequest: CodeRequest
    ) : Response<CodeResponse>

    @POST("api/v1/likes/{post_id}")
    suspend fun postLike(
        @Path("post_id") postId: String
    ) : Response<LikeResponse>

    @POST("api/v1/comments/{post_id}")
    suspend fun postComment(
        @Path("post_id") postId: String,
        @Body content: CommentRequest
    ) : Response<CodeResponse>
}