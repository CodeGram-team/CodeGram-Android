package com.code.gram.data.datasource.home

import com.code.gram.data.dto.request.CodeRequest
import com.code.gram.data.dto.request.CommentRequest
import com.code.gram.data.service.HomeService
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService: HomeService
) {
    suspend fun getPosts(page: Int, sortType: String) = homeService.getCodeList(page = page, sortBy = sortType)

    suspend fun postCode(codeRequest: CodeRequest) = homeService.postCode(codeRequest)

    suspend fun postLike(postId: String) = homeService.postLike(postId)

    suspend fun postComment(postId: String, content: CommentRequest) = homeService.postComment(postId, content)
}