package com.code.gram.domain.repository

import com.code.gram.domain.entity.home.CodeResponseEntity

interface SearchRepository {
    suspend fun postSearch(
        vibeEmojis: List<String>? = null,
        language: String? = null,
        tags: List<String>? = null,
        query: String? = null,
        page: Int = 1,
        size: Int = 10,
    ) : Result<List<CodeResponseEntity>>
}