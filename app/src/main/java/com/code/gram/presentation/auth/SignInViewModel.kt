package com.code.gram.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.domain.usecase.auth.PostGoogleSignInUseCase
import com.code.gram.domain.usecase.auth.PostLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val postGoogleSignInUseCase: PostGoogleSignInUseCase,
) : ViewModel() {

    private val _authSideEffect : MutableSharedFlow<AuthSideEffect> = MutableSharedFlow()
    val authSideEffect : SharedFlow<AuthSideEffect> = _authSideEffect.asSharedFlow()

    fun onGoogleSignClick(context: Context) {
        viewModelScope.launch {
            postGoogleSignInUseCase(context)
                .onSuccess { idToken ->
                    Timber.d("Google ID Token: $idToken")
                    postLoginUseCase(idToken)
                        .onSuccess { authResult ->
                            Timber.d("Auth Result: $authResult")
                            _authSideEffect.emit(AuthSideEffect.NavigateHome)
                        }
                        .onFailure {
                            Timber.e(it)
                        }
                }
                .onFailure {
                    Timber.e(it)
                }
        }
    }
}