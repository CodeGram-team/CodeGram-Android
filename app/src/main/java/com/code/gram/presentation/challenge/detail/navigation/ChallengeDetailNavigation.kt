package com.code.gram.presentation.challenge.detail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.code.gram.presentation.challenge.detail.ChallengeDetailRoute
import kotlinx.serialization.Serializable

fun NavGraphBuilder.challengeDetailGraph(
    paddingValues: PaddingValues,
    navigateUp: () -> Unit
) {
    composable<ChallengeDetail> {
        ChallengeDetailRoute(
            paddingValues = paddingValues,
            navigateUp = navigateUp
        )
    }
}

@Serializable
data class ChallengeDetail(val challengeId: Int)