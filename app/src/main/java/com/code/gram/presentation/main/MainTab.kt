package com.code.gram.presentation.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.example.makersassignment.R
import com.example.makersassignment.core.navigation.MainTabRoute
import com.example.makersassignment.core.navigation.Route
import com.code.gram.presentation.home.navigation.Home
import com.code.gram.presentation.mypage.navigation.MyPage
import kotlin.collections.any
import kotlin.collections.find
import kotlin.collections.map

enum class MainTab(
    @param:DrawableRes val selectedIcon: Int,
    @param:DrawableRes val unselectedIcon: Int,
    @param:StringRes val contentDescription: Int,
    val route: MainTabRoute,
) {
    HOME(
        selectedIcon = R.drawable.ic_home_fill,
        unselectedIcon = R.drawable.ic_home_linear,
        contentDescription = R.string.ic_home_description,
        route = Home,
    ),

    MYPAGE(
        selectedIcon = R.drawable.ic_mypage_fill,
        unselectedIcon = R.drawable.ic_mypage_linear,
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