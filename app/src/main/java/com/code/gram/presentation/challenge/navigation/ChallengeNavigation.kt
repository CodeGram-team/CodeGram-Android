package com.code.gram.presentation.challenge.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.code.gram.presentation.challenge.detail.navigation.ChallengeDetail
import com.code.gram.presentation.challenge.detail.navigation.challengeDetailGraph
import com.code.gram.presentation.challenge.list.navigation.ChallengeList
import com.code.gram.presentation.challenge.list.navigation.challengeListGraph
import com.example.makersassignment.core.navigation.MainTabRoute
import kotlinx.serialization.Serializable

fun NavController.navigateChallengeDetail(
    navOptions: NavOptions?,
    challengeId: Int
) {
    navigate(ChallengeDetail(challengeId), navOptions)
}

fun NavController.navigateChallengeList(
    navOptions: NavOptions?
) {
    navigate(ChallengeList, navOptions)
}

fun NavController.navigateChallengeGraph(
    navOptions: NavOptions?
) {
    navigate(ChallengeGraph, navOptions)
}

fun NavGraphBuilder.challengeGraph(
    paddingValues: PaddingValues,
    navController: NavController,
    navigateUp: () -> Unit
) {
    navigation<ChallengeGraph>(
        startDestination = ChallengeList,
    ) {
        challengeDetailGraph(
            paddingValues = paddingValues,
            navigateUp = navigateUp
        )

        challengeListGraph(
            paddingValues = paddingValues,
            navigateUp = navigateUp,
            navigateChallengeDetail = {
                navController.navigateChallengeDetail(null, it)
            }
        )
    }
}

@Serializable
data object ChallengeGraph

