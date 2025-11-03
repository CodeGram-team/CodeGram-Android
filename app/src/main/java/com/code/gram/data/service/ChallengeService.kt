package com.code.gram.data.service

import com.code.gram.data.dto.request.ChallengeRequest
import com.code.gram.data.dto.response.ChallengeDifficultyResponse
import com.code.gram.data.dto.response.ChallengePostResponse
import com.code.gram.data.dto.response.ChallengeProblemResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ChallengeService {
    @GET("api/v1/challenges")
    suspend fun getChallenges(
        @Query("difficulty") difficulty: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<List<ChallengeDifficultyResponse>>

    @GET("api/v1/challenge/{problem_id}")
    suspend fun getChallenge(
        @Path("problem_id") problemId: Int,
    ): Response<ChallengeProblemResponse>


    @POST("api/v1/challenge/{problem_id}/submit")
    suspend fun submitCode(
        @Path("problem_id") problemId: Int,
        @Body challengeRequest: ChallengeRequest
    ): Response<ChallengePostResponse>
}