package com.code.gram.presentation.search

import androidx.lifecycle.ViewModel
import com.code.gram.presentation.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    fun updateSearchQuery(query: String) {
        _state.update {
            it.copy(
                searchText = query
            )
        }
    }
}