package com.code.gram.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.domain.usecase.auth.GetInfoUseCase
import com.code.gram.domain.usecase.auth.PostGoogleSignInUseCase
import com.code.gram.domain.usecase.auth.PostLoginUseCase
import com.code.gram.domain.usecase.auth.PostServerLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val postLoginUseCase: PostLoginUseCase,
    private val postGoogleSignInUseCase: PostGoogleSignInUseCase,
    private val postServerLoginUseCase: PostServerLoginUseCase,
    private val getSignUpTokenUseCase: GetInfoUseCase
) : ViewModel() {
    private val _state : MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val state : StateFlow<SignUpState> = _state.asStateFlow()

    private val _authSideEffect : MutableSharedFlow<AuthSideEffect> = MutableSharedFlow()
    val authSideEffect : SharedFlow<AuthSideEffect> = _authSideEffect.asSharedFlow()

    val signupToken: StateFlow<String> = getSignUpTokenUseCase()
        .filterNotNull()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ""
        )

    var test = ""

    fun onTextChanged(nickname: String) {
       _state.update {
           it.copy(nickname = nickname)
       }
    }

    fun onGoogleSignClick(context: Context) {
        viewModelScope.launch {
            postGoogleSignInUseCase(context)
                .onSuccess { idToken ->
                    Timber.d("Google ID Token: $idToken")
                    postLoginUseCase(idToken)
                        .onSuccess { authResult ->
                            Timber.d("Auth Result: $authResult")
                            if (authResult.status == "signup") {
                                test = authResult.signupToken
                                _state.update {
                                    it.copy(isVisibleNickName = true)
                                }
                            } else {
                                _authSideEffect.emit(AuthSideEffect.NavigateHome)
                            }
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

    fun signUp() {
        Timber.d("SignUp Token: ${signupToken.value}")
        viewModelScope.launch {
            postServerLoginUseCase(test, _state.value.nickname)
                .onSuccess {
                    Timber.d("Server Login Result: $it")
                    _authSideEffect.emit(AuthSideEffect.NavigateHome)
                }
                .onFailure {
                    Timber.e(it)
                }
        }
    }
}