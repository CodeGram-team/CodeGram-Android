package com.code.gram.presentation.home

import com.code.gram.core.model.FeedModel
import com.example.makersassignment.core.common.util.UiState

//@Immutable
data class HomeState(
    /*val feedItem : UiState<FeedModel> = UiState.Loading,
    val fakeItem : List<FeedModel> = emptyList()*/

    val page : Int = 1,
    val feedItem : UiState<List<FeedModel>> = UiState.Loading,
    val isSuccess : Boolean = false,
    val codeResult : String= "",
    val userInput : String = "",
    val isLoading : Boolean = false,
)