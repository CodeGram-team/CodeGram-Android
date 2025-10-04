package com.code.gram.presentation.main

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import com.code.gram.presentation.auth.navigation.authGraph
import com.code.gram.presentation.challenge.navigation.challengeGraph
import com.code.gram.presentation.home.navigation.homeGraph
import com.code.gram.presentation.main.component.MainBottomBar
import com.code.gram.presentation.mypage.navigation.myPageGraph
import com.code.gram.presentation.post.navigation.postGraph
import com.code.gram.presentation.search.navigation.searchGraph
import kotlinx.collections.immutable.toPersistentList

private const val EXIT_MILLIS = 3000L

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator(),
) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableLongStateOf(0L) }

    val exitToast = remember {
        Toast.makeText(context, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT)
    }

    if (navigator.currentTab != null) {
        BackHandler {
            val currentTime = System.currentTimeMillis()
            if (currentTime - backPressedTime <= EXIT_MILLIS) {
                (context as? Activity)?.finish()
            } else {
                backPressedTime = currentTime
                exitToast.show()
            }
        }
    }

    Scaffold (
        bottomBar = {
            MainBottomBar(
                isVisible = navigator.showBottomBar(),
                tabs = MainTab.entries.toPersistentList(),
                currentTab = navigator.currentTab,
                onTabSelected = navigator::navigate,
            )
        },
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .systemBarsPadding()
            .fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navigator.navController,
            startDestination = navigator.startDestination,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> -fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth },
                    animationSpec = tween(durationMillis = 300)
                )
            },
        ) {
            authGraph(
                navigateHome = navigator::navigateHomeFromLogin,
                navigateUp = navigator::navigateUp,
            )

            homeGraph(
                paddingValues = innerPadding,
            )

            searchGraph(
                paddingValues = innerPadding,
                navigateUp = navigator::navigateUp,
            )

            challengeGraph(
                paddingValues = innerPadding,
                navigateUp = navigator::navigateUp,
            )

            postGraph(
                paddingValues = innerPadding,
                navigateUp = navigator::navigateUp,
            )

            myPageGraph(
                paddingValues = innerPadding
            )
        }
    }
}