package com.code.gram.presentation.search.state

import com.code.gram.core.model.FeedModel
import com.code.gram.presentation.search.model.SearchQueryUiModel

data class SearchState(
    val isLoading : Boolean = false,
    val searchResultList : List<FeedModel> = emptyList(),
    val searchQueryUiModel: SearchQueryUiModel = SearchQueryUiModel(),
    val codeResult : String = "",
    val userInput : String = "",
    val isSuccess : Boolean = false,
    val isFavorite : Boolean = false
)
