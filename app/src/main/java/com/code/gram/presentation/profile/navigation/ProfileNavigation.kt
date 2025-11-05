package com.code.gram.presentation.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.code.gram.presentation.mypage.MyPageRoute
import com.code.gram.presentation.profile.ProfileRoute
import com.example.makersassignment.core.navigation.MainTabRoute
import com.example.makersassignment.core.navigation.Route
import kotlinx.serialization.Serializable

fun NavController.navigateProfile(
    navOptions: NavOptions?,
    nickname: String
) {
    navigate(Profile(nickname), navOptions)
}

fun NavGraphBuilder.profileGraph(
    paddingValues: PaddingValues,
) {
    composable<Profile> {
        ProfileRoute(
            paddingValues = paddingValues,
        )
    }
}

@Serializable
data class Profile(val nickname : String) : Route