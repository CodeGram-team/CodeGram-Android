package com.code.gram.presentation.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.code.gram.domain.repository.ProfileRepository
import com.code.gram.presentation.mypage.model.toUiModel
import com.code.gram.presentation.mypage.state.MyPageState
import com.code.gram.presentation.profile.navigation.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state = _state.asStateFlow()

    val nickname = savedStateHandle.toRoute<Profile>().nickname

    init {
        fetchUserInfo()
    }

    fun fetchUserInfo() {
        viewModelScope.launch {
            profileRepository.getUserInfoByNickname(nickname)
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