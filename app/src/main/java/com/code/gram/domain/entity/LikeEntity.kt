package com.code.gram.domain.entity

data class LikeEntity(
    val postId: String,
    val userId: String,
    val likeCount: Int,
    val userHasLiked: Boolean
)
