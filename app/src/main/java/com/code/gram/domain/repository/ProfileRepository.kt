package com.code.gram.domain.repository

import com.code.gram.domain.entity.profile.UserInfoEntity

interface ProfileRepository {
    suspend fun getUserInfo(): Result<UserInfoEntity>
    suspend fun getUserInfoByNickname(nickname: String): Result<UserInfoEntity>
}