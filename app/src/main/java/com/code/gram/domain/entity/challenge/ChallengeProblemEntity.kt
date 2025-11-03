package com.code.gram.domain.entity.challenge

data class ChallengeProblemEntity(
    val problemId: Int,
    val difficulty: String,
    val question: String,
    val starterCode: String,
    val url: String
)