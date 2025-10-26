package com.code.gram.presentation.search.state

import com.code.gram.presentation.search.model.SearchItemModel

data class SearchState(
    val searchText: String = "",
    val searchList : List<SearchItemModel> = emptyList(),
)
