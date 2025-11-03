package com.code.gram.data.repositoryimpl

import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.profile.ProfileDataSource
import com.code.gram.domain.entity.profile.UserInfoEntity
import com.code.gram.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileDataSource: ProfileDataSource
) : ProfileRepository {
    override suspend fun getUserInfo(): Result<UserInfoEntity> = suspendRunCatching{
        profileDataSource.getUserInfo().body()!!.toDomain()
    }

    override suspend fun getUserInfoByNickname(nickname: String): Result<UserInfoEntity> = suspendRunCatching{
        profileDataSource.getUserInfoByNickname(nickname).body()!!.toDomain()
    }

}