package com.code.gram.data.repositoryimpl

import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.home.HomeDataSource
import com.code.gram.data.dto.request.CommentRequest
import com.code.gram.data.dto.request.toData
import com.code.gram.data.dto.response.toDomain
import com.code.gram.data.util.parseErrorArray
import com.code.gram.domain.entity.LikeEntity
import com.code.gram.domain.entity.home.CodeRequestEntity
import com.code.gram.domain.entity.home.CodeResponseEntity
import com.code.gram.domain.exception.CodeRejectedException
import com.code.gram.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getPosts(page: Int, sortType: String): Result<List<CodeResponseEntity>> = suspendRunCatching {
        homeDataSource.getPosts(page, sortType).body()!!.map { it.toDomain() }
    }

    override suspend fun postCode(codeRequestEntity: CodeRequestEntity): Result<CodeResponseEntity> = suspendRunCatching {
        val response = homeDataSource.postCode(codeRequest = codeRequestEntity.toData())

        if (response.isSuccessful) {
            response.body()!!.toDomain()
        } else {
            val errorBody = response.errorBody()?.string()
            val errorItems = errorBody?.let { parseErrorArray(it) } ?: emptyList()
            throw CodeRejectedException(errorItems)
        }
    }

    override suspend fun postLike(postId: String): Result<LikeEntity> = suspendRunCatching{
        homeDataSource.postLike(postId).body()!!.toDomain()
    }

    override suspend fun postComment(postId: String, content: String): Result<CodeResponseEntity> = suspendRunCatching{
        val request = CommentRequest(
            content = content
        )
        homeDataSource.postComment(postId, request).body()!!.toDomain()
    }
}