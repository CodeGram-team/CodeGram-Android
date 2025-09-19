package com.code.gram.presentation.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.code.gram.presentation.auth.SignInRoute
import com.example.makersassignment.core.navigation.Route
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authGraph(
    navigateUp: () -> Unit,
    navigateHome: () -> Unit,
) {
    composable<Auth> {
        SignInRoute(

        )
    }
}

@Serializable
data object Auth : Route