package com.code.gram.domain.repository

import com.code.gram.domain.entity.challenge.ChallengeDifficultyEntity
import com.code.gram.domain.entity.challenge.ChallengePostEntity
import com.code.gram.domain.entity.challenge.ChallengeProblemEntity

interface ChallengeRepository {
    suspend fun getDifficultyChallenges(difficulty: String, page: Int, size: Int): Result<List<ChallengeDifficultyEntity>>
    suspend fun getChallenge(problemId: Int): Result<ChallengeProblemEntity>
    suspend fun postChallenge(problemId: Int, language: String, code: String): Result<ChallengePostEntity>

}