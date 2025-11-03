package com.code.gram.data.dto.response

import com.code.gram.domain.entity.LikeEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeResponse(
    @SerialName("post_id")
    val postId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("likes_count")
    val likeCount: Int,
    @SerialName("user_has_liked")
    val userHasLiked: Boolean
)

fun LikeResponse.toDomain() = LikeEntity(
    postId = postId,
    userId = userId,
    likeCount = likeCount,
    userHasLiked = userHasLiked
)