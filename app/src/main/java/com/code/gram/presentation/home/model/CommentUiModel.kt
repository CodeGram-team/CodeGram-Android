package com.code.gram.presentation.home.model

import com.code.gram.domain.entity.home.CommentResponseEntity

data class CommentUiModel (
    val commentId: String,
    val authorId: String,
    val authorNickname: String,
    val content: String,
    val createdAt: String
)

fun CommentResponseEntity.toUiModel() = CommentUiModel(
    commentId = commentId,
    authorId = authorId,
    authorNickname = authorNickname,
    content = content,
    createdAt = createdAt
)