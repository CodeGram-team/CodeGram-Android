package com.code.gram.presentation.search.model

data class SearchQueryUiModel(
    val vibeEmojis: List<String> = emptyList(),
    val language: String? = null,
    val tags: List<String> = emptyList(),
    val query: String? = null,
    val page: Int = 1,
    val size: Int = 10,
)
