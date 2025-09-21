package com.code.gram.presentation.challenge.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.code.gram.presentation.auth.SignInRoute
import com.code.gram.presentation.challenge.ChallengeRoute
import com.example.makersassignment.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateChallenge(
    navOptions: NavOptions?
) {
    navigate(Challenge, navOptions)
}

fun NavGraphBuilder.challengeGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
) {
    composable<Challenge> {
        ChallengeRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data object Challenge : MainTabRoute