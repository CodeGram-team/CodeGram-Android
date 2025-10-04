package com.code.gram.presentation.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.code.gram.presentation.search.SearchRoute
import com.example.makersassignment.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateSearch(
    navOptions: NavOptions?,
) {
    navigate(Search, navOptions)
}

fun NavGraphBuilder.searchGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Search> {
        SearchRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data object Search : MainTabRoute