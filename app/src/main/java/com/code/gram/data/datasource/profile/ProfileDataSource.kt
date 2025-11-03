package com.code.gram.data.datasource.profile

import com.code.gram.data.service.ProfileService
import javax.inject.Inject

class ProfileDataSource @Inject constructor(
    private val profileService: ProfileService
) {
    suspend fun getUserInfo() = profileService.getUserInfo()

    suspend fun getUserInfoByNickname(nickname: String) = profileService.getUserInfoByNickname(nickname)
}