package com.code.gram.domain.entity.home

data class CodeResponseEntity(
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
    val comments: List<CommentResponseEntity>,
    val createdAt: String
)

data class CommentResponseEntity(
    val commentId: String,
    val authorId: String,
    val authorNickname: String,
    val content: String,
    val createdAt: String
)