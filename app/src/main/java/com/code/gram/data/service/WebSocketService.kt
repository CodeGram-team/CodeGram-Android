package com.code.gram.data.service

import com.code.gram.data.dto.request.JopRequest
import com.code.gram.data.dto.response.JopResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WebSocketService {
    @POST("api/v1/stream")
    suspend fun startJob(@Body request: JopRequest): Response<JopResponse>
}