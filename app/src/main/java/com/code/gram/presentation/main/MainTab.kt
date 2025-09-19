package com.code.gram.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.code.gram.R
import com.code.gram.presentation.challenge.navigation.Challenge
import com.code.gram.presentation.home.navigation.Home
import com.code.gram.presentation.mypage.navigation.MyPage
import com.code.gram.presentation.post.navigation.Post
import com.example.makersassignment.core.navigation.MainTabRoute
import com.example.makersassignment.core.navigation.Route

enum class MainTab(
    @param:DrawableRes val selectedIcon: Int,
    @param:DrawableRes val unselectedIcon: Int,
    @param:StringRes val contentDescription: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home_filled,
        contentDescription = R.string.ic_home_description,
        route = Home,
    ),

    CHANLLENGE(
        selectedIcon = R.drawable.ic_challenge,
        unselectedIcon = R.drawable.ic_challenge_filled,
        contentDescription = R.string.ic_challenge_description,
        route = Challenge,
    ),

    POST(
        selectedIcon = R.drawable.ic_add,
        unselectedIcon = R.drawable.ic_add_filled,
        contentDescription = R.string.ic_post_description,
        route = Post,
    ),

    MYPAGE(
        selectedIcon = R.drawable.ic_person,
        unselectedIcon = R.drawable.ic_person_filled,
        contentDescription = R.string.ic_mypage_description,
        route = MyPage,
    );

    companion object {
        @Composable
        fun find(predicate: @Composable (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        @Composable
        fun contains(predicate: @Composable (Route) -> Boolean): Boolean {
            return entries.map { it.route }.any { predicate(it) }
        }
    }
}