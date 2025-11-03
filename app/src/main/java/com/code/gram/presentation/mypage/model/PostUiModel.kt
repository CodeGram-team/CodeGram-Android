package com.code.gram.presentation.mypage.model

import com.code.gram.domain.entity.profile.PostEntity

data class PostUiModel(
    val id: String,
    val title: String,
    val language: String,
    val likesCount: Int,
    val createdAt: String
)

fun PostEntity.toUiModel() = PostUiModel(
    id = id,
    title = title,
    language = language,
    likesCount = likesCount,
    createdAt = createdAt
)
