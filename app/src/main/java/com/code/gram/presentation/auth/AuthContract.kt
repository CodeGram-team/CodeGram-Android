package com.code.gram.presentation.auth

import androidx.compose.runtime.Immutable

@Immutable
data class SignUpState(
    val nickname : String = "",
    val isVisibleNickName : Boolean = false
)

sealed interface AuthSideEffect {
    data object NavigateHome : AuthSideEffect
}