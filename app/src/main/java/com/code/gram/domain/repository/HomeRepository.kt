package com.code.gram.domain.repository

import com.code.gram.domain.entity.LikeEntity
import com.code.gram.domain.entity.home.CodeRequestEntity
import com.code.gram.domain.entity.home.CodeResponseEntity

interface HomeRepository {
    suspend fun getPosts(page: Int): Result<List<CodeResponseEntity>>
    suspend fun postCode(codeRequestEntity: CodeRequestEntity): Result<CodeResponseEntity>

    suspend fun postLike(postId: String): Result<LikeEntity>

    suspend fun postComment(postId: String, content: String): Result<CodeResponseEntity>
}