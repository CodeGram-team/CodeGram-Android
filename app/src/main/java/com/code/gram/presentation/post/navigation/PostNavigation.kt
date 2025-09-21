package com.code.gram.presentation.post.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.code.gram.presentation.post.PostRoute
import com.example.makersassignment.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigatePost(
    navOptions: NavOptions?,
) {
    navigate(Post, navOptions)
}

fun NavGraphBuilder.postGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Post> {
        PostRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data object Post : MainTabRoute