package com.code.gram.presentation.mypage.state

import com.code.gram.presentation.mypage.model.PostUiModel

data class MyPageState(
    val id: String = "",
    val nickname: String = "",
    val username: String = "",
    val profileImageUrl: String = "",
    val createdAt: String = "",
    val posts: List<PostUiModel> = emptyList(),
    val email: String = ""
)