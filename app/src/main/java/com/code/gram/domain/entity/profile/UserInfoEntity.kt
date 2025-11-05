package com.code.gram.domain.entity.profile

data class UserInfoEntity(
    val id: String,
    val nickname: String,
    val username: String,
    val profileImageUrl: String,
    val createdAt: String,
    val posts: List<PostEntity>,
    val email: String?
)

data class PostEntity(
    val id: String,
    val title: String,
    val language: String,
    val likesCount: Int,
    val createdAt: String
)
