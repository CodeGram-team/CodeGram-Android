package com.code.gram.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.domain.repository.ProfileRepository
import com.code.gram.presentation.mypage.model.toUiModel
import com.code.gram.presentation.mypage.state.MyPageState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state = _state.asStateFlow()

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            profileRepository.getUserInfo()
                .onSuccess { userInfo ->
                    _state.update { currentState ->
                        currentState.copy(
                            id = userInfo.id,
                            nickname = userInfo.nickname,
                            username = userInfo.username,
                            profileImageUrl = userInfo.profileImageUrl,
                            createdAt = userInfo.createdAt,
                            posts = userInfo.posts.map { it.toUiModel() },
                            email = userInfo.email ?: ""
                        )
                    }
                }
                .onFailure {
                    Timber.e(it)
                }
        }
    }
}