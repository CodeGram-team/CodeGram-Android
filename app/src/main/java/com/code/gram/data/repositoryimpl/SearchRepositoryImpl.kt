package com.code.gram.data.repositoryimpl

import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.search.SearchDataSource
import com.code.gram.domain.entity.home.CodeResponseEntity
import com.code.gram.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: SearchDataSource
) : SearchRepository {

    override suspend fun postSearch(
        vibeEmojis: List<String>?,
        language: String?,
        tags: List<String>?,
        query: String?,
        page: Int,
        size: Int
    ): Result<List<CodeResponseEntity>> = suspendRunCatching {
        val response = dataSource.postSearch(
            vibeEmojis,
            language,
            tags,
            query,
            page,
            size
        )

        if (response.isSuccessful) {
            response.body()!!.map { it.toDomain() }
        } else {
            throw Exception("Search failed: ${response.errorBody()?.string()}")
        }
    }
}
