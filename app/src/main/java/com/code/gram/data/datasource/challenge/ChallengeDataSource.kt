package com.code.gram.data.datasource.challenge

import com.code.gram.data.dto.request.ChallengeRequest
import com.code.gram.data.dto.request.CodeRequest
import com.code.gram.data.service.ChallengeService
import com.code.gram.data.service.HomeService
import javax.inject.Inject

class ChallengeDataSource @Inject constructor(
    private val challengeService: ChallengeService
) {
    suspend fun getDifficultyChallenges(difficulty: String, page: Int, size: Int) = challengeService.getChallenges(difficulty, page, size)

    suspend fun getChallenge(problemId: Int) = challengeService.getChallenge(problemId)

    suspend fun postChallenge(problemId: Int, challengeRequest: ChallengeRequest) = challengeService.submitCode(problemId, challengeRequest)

}