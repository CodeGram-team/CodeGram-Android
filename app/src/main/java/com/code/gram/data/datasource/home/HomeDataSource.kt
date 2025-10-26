package com.code.gram.data.datasource.home

import com.code.gram.data.dto.request.CodeRequest
import com.code.gram.data.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun getPosts() = homeService.getCodeList()

    suspend fun postCode(codeRequest: CodeRequest) = homeService.postCode(codeRequest)
}