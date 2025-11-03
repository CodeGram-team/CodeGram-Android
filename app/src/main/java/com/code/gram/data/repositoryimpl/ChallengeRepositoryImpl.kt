package com.code.gram.data.repositoryimpl

import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.challenge.ChallengeDataSource
import com.code.gram.data.dto.request.ChallengeRequest
import com.code.gram.domain.entity.challenge.ChallengeDifficultyEntity
import com.code.gram.domain.entity.challenge.ChallengePostEntity
import com.code.gram.domain.entity.challenge.ChallengeProblemEntity
import com.code.gram.domain.repository.ChallengeRepository
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val challengeDataSource: ChallengeDataSource
) : ChallengeRepository {
    override suspend fun getDifficultyChallenges(
        difficulty: String,
        page: Int,
        size: Int
    ): Result<List<ChallengeDifficultyEntity>> = suspendRunCatching{
        challengeDataSource.getDifficultyChallenges(difficulty, page, size).body()!!.map { it.toDomain() }
    }

    override suspend fun getChallenge(problemId: Int): Result<ChallengeProblemEntity> = suspendRunCatching {
        challengeDataSource.getChallenge(problemId).body()!!.toDomain()
    }

    override suspend fun postChallenge(
        problemId: Int,
        language: String,
        code: String
    ): Result<ChallengePostEntity> = suspendRunCatching{
        val request = ChallengeRequest(language, code)
        challengeDataSource.postChallenge(problemId, challengeRequest = request).body()!!.toDomain()
    }
}