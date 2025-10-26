package com.code.gram.data.repositoryimpl

import com.code.gram.core.common.util.suspendRunCatching
import com.code.gram.data.datasource.home.HomeDataSource
import com.code.gram.data.dto.request.toData
import com.code.gram.domain.entity.home.CodeRequestEntity
import com.code.gram.domain.entity.home.CodeResponseEntity
import com.code.gram.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource
) : HomeRepository {
    override suspend fun getPosts(): Result<List<CodeResponseEntity>> = suspendRunCatching {
        homeDataSource.getPosts().body()!!.map { it.toDomain() }
    }

    override suspend fun postCode(codeRequestEntity: CodeRequestEntity): Result<CodeResponseEntity> = suspendRunCatching {
        homeDataSource.postCode(
            codeRequest = codeRequestEntity.toData()
        ).body()!!.toDomain()
    }
}