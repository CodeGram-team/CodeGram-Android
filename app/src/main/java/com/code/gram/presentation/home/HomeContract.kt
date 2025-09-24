package com.code.gram.presentation.home

import androidx.compose.runtime.Immutable
import com.code.gram.presentation.home.model.FeedModel
import com.example.makersassignment.core.common.util.UiState

//@Immutable
data class HomeState(
    val feedItem : UiState<FeedModel> = UiState.Loading,
    val fakeItem : List<FeedModel> = emptyList()
)