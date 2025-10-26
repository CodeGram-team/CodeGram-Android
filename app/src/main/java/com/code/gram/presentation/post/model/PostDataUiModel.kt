package com.code.gram.presentation.post.model

import com.code.gram.domain.entity.home.CodeRequestEntity

data class PostDataUiModel(
    val title: String = "",
    val description: String = "",
    val code: String = "",
    val language: String = "",
    val tags: List<String> = emptyList(),
)

fun PostDataUiModel.toEntity() = CodeRequestEntity(
    title = title,
    description = description,
    code = code,
    language = language,
    tags = tags
)
