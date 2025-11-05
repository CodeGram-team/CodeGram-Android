package com.code.gram.data.dto.response

import com.code.gram.domain.entity.profile.PostEntity
import com.code.gram.domain.entity.profile.UserInfoEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class UserInfoResponse(
    @SerialName("id")
    val id: String,

    @SerialName("nickname")
    val nickname: String,

    @SerialName("username")
    val username: String,

    @SerialName("profile_image_url")
    val profileImageUrl: String,

    @SerialName("created_at")
    val createdAt: String, // 필요시 Instant로 변환 가능

    @SerialName("posts")
    val posts: List<Post>,

    @SerialName("email")
    val email: String? = null
) {
    fun toDomain(): UserInfoEntity {
        return UserInfoEntity(
            id = id,
            nickname = nickname,
            username = username,
            profileImageUrl = profileImageUrl,
            createdAt = createdAt,
            posts = posts.map { it.toDomain() },
            email = email
        )
    }
}

@Serializable
data class Post(
    @SerialName("_id")
    val id: String,

    @SerialName("title")
    val title: String,

    @SerialName("language")
    val language: String,

    @SerialName("likesCount")
    val likesCount: Int,

    @SerialName("createdAt")
    val createdAt: String
) {
    fun toDomain(): PostEntity = PostEntity (
        id = id,
        title = title,
        language = language,
        likesCount = likesCount,
        createdAt = createdAt
    )
}