package com.code.gram.presentation.post.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.code.gram.presentation.auth.SignInRoute
import com.example.makersassignment.core.navigation.MainTabRoute
import com.example.makersassignment.core.navigation.Route
import kotlinx.serialization.Serializable

fun NavGraphBuilder.postGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateHome: () -> Unit,
) {
    composable<Post> {
        PostRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data object Post : MainTabRoute