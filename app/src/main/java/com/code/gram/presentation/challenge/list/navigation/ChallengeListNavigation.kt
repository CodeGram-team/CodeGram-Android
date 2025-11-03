package com.code.gram.presentation.challenge.list.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.code.gram.presentation.challenge.list.ChallengeListRoute
import com.example.makersassignment.core.navigation.MainTabRoute
import com.example.makersassignment.core.navigation.Route
import kotlinx.serialization.Serializable

fun NavGraphBuilder.challengeListGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit,
    navigateChallengeDetail: (Int) -> Unit
) {
    composable<ChallengeList> {
        ChallengeListRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateChallengeDetail = navigateChallengeDetail
        )
    }
}

@Serializable
data object ChallengeList : MainTabRoute