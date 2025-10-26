package com.code.gram.data.dto.response

import com.code.gram.domain.entity.home.CodeResponseEntity
import com.code.gram.domain.entity.home.CommentResponseEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeResponse(
    @SerialName("_id")
    val id: String,
    @SerialName("authorId")
    val authorId: String,
    @SerialName("authorNickname")
    val authorNickname: String,
    @SerialName("authorProfileImageUrl")
    val authorProfileImageUrl: String? = null,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String? = null,
    @SerialName("code")
    val code: String,
    @SerialName("language")
    val language: String,
    @SerialName("tags")
    val tags: List<String>,
    @SerialName("vibeEmojis")
    val vibeEmojis: List<String>,
    @SerialName("likesCount")
    val likesCount: Int,
    @SerialName("comments")
    val comments: List<CommentResponse>,
    @SerialName("createdAt")
    val createdAt: String
) {
    fun toDomain() = CodeResponseEntity(
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
        comments = comments.map { it.toDomain() },
        createdAt = createdAt
    )
}

@Serializable
data class CommentResponse(
    @SerialName("commentId")
    val commentId: String,
    @SerialName("authorId")
    val authorId: String,
    @SerialName("authorNickname")
    val authorNickname: String,
    @SerialName("content")
    val content: String,
    @SerialName("createdAt")
    val createdAt: String
) {
    fun toDomain() = CommentResponseEntity(
        commentId = commentId,
        authorId = authorId,
        authorNickname = authorNickname,
        content = content,
        createdAt = createdAt
    )
}
