package com.code.gram.presentation.home.model

import androidx.compose.runtime.Immutable
import com.code.gram.domain.entity.home.CodeResponseEntity

@Immutable
data class FeedModel(
    val id: String,
    val authorId: String,
    val authorNickname: String,
    val authorProfileImageUrl: String?,
    val title: String,
    val description: String?,
    val code: String,
    val language: String,
    val tags: List<String>,
    val vibeEmojis: List<String>,
    val likesCount: Int,
    val comments: List<CommentUiModel>,
    val createdAt: String
)

fun CodeResponseEntity.toUiModel() = FeedModel(
    id = id,
    authorId = authorId,
    authorNickname = authorNickname,
    authorProfileImageUrl = authorProfileImageUrl,
    title = title,
    description = description,
    code = code,
    language = language,
    tags = tags,
    vibeEmojis = vibeEmojis,
    likesCount = likesCount,
    comments = comments.map { it.toUiModel() },
    createdAt = createdAt
)