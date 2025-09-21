package com.code.gram.presentation.auth

sealed interface AuthSideEffect {
    data object NavigateHome : AuthSideEffect
}