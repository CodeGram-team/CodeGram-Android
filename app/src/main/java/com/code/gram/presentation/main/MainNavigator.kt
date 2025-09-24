package com.code.gram.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.code.gram.presentation.challenge.navigation.navigateChallenge
import com.code.gram.presentation.home.navigation.Home
import com.code.gram.presentation.home.navigation.navigateHome
import com.code.gram.presentation.mypage.navigation.navigateMyPage
import com.code.gram.presentation.post.navigation.navigatePost

class MainNavigator(
    val navController: NavHostController,
) {
    private val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val startDestination = Home

    val currentTab: MainTab?
        @Composable get() = MainTab.find { tab ->
            currentDestination?.hasRoute(tab::class) == true
        }

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            MainTab.HOME -> navController.navigateHome(navOptions)
            MainTab.MYPAGE -> navController.navigateMyPage(navOptions)
            MainTab.CHANLLENGE -> navController.navigateChallenge(navOptions)
            MainTab.POST -> navController.navigatePost(navOptions)
        }
    }

    fun navigateUp() {
        navController.navigateUp()
    }

    fun navigateHomeFromLogin() {
        val navOptions = navOptions {
            popUpTo(0) {
                inclusive = true
            }
            launchSingleTop = true
        }
        navController.navigateHome(navOptions)
    }

    @Composable
    fun showBottomBar() = MainTab.contains {
        currentDestination?.hasRoute(it::class) == true
    }
}

@Composable
fun rememberMainNavigator(
    navController: NavHostController = rememberNavController(),
): MainNavigator = remember(navController) {
    MainNavigator(navController)
}
