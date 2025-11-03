package com.code.gram.data.datasource.search

import com.code.gram.data.service.SearchService
import javax.inject.Inject

class SearchDataSource @Inject constructor(
    private val searchService: SearchService
) {
    suspend fun postSearch(
        vibeEmojis: List<String>? = null,
        language: String? = null,
        tags: List<String>? = null,
        query: String? = null,
        page: Int = 1,
        size: Int = 10,
    ) = searchService.postSearch(
        vibeEmojis = vibeEmojis,
        language = language,
        tags = tags,
        query = query,
        page = page,
        size = size,
    )
}