package com.code.gram.domain.repository

import com.code.gram.domain.entity.home.CodeRequestEntity
import com.code.gram.domain.entity.home.CodeResponseEntity

interface HomeRepository {
    suspend fun getPosts(): Result<List<CodeResponseEntity>>
    suspend fun postCode(codeRequestEntity: CodeRequestEntity): Result<CodeResponseEntity>
}