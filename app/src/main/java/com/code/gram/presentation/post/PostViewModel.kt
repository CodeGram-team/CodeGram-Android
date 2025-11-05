package com.code.gram.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.gram.domain.exception.CodeRejectedException
import com.code.gram.domain.repository.HomeRepository
import com.code.gram.domain.repository.WebSocketRepository
import com.code.gram.presentation.post.model.PostDataUiModel
import com.code.gram.presentation.post.model.toEntity
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val webSocketRepository: WebSocketRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun postComplete() {
        viewModelScope.launch {
            homeRepository.postCode(
                PostDataUiModel(
                    title = _state.value.post.title,
                    description = _state.value.post.description,
                    code = _state.value.post.code,
                    language = _state.value.post.language.lowercase(),
                    tags = _state.value.post.tags
                ).toEntity()
            ).onSuccess {
                Timber.e("success postCode")
                _state.update {
                    it.copy(
                        isSuccess = true,
                        error = ""
                    )
                }
            }.onFailure { throwable ->
                if (throwable is CodeRejectedException) {
                    Timber.e("throwable ${throwable}")
                    val errorReasons = throwable.errorItems.map {
                        "${it.loc.joinToString(".")} : ${it.msg}"
                    }
                    _state.update {
                        it.copy(
                            error = throwable.message ?: "코드 검증 실패",
                            errorReasons = errorReasons
                        )
                    }
                } else {
                    Timber.e("throwable else $throwable")

                    val inputValue = throwable.message
                        ?.substringAfter("JSON input:", "")
                        ?.trim()
                        ?.trim('"')  // 문자열 양쪽 큰따옴표 제거
                        ?: "코드 검증 실패"

                    _state.update {
                        it.copy(
                            error = inputValue
                        )
                    }
                }
            }
        }
    }
    fun startJob() {
        viewModelScope.launch {
            webSocketRepository.startJob(
                language = _state.value.post.language,
                code = _state.value.post.code
            ).onSuccess { result ->
                Timber.e("success ${result}")

                observeMessages()
            }.onFailure {
                Timber.e("fail ${it}")
            }
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            webSocketRepository.observeJobResult().collect { result ->
                _state.update {
                    it.copy(
                        codeResult = it.codeResult + "\n[${result.type}] ${result.data}"
                    )
                }
                Timber.e("result ${result}")
            }
        }
    }

    fun sendInput() {
        val input = _state.value.userInput
        webSocketRepository.sendInput(input)
        _state.update {
            it.copy(
                userInput = ""
            )
        }
    }

    override fun onCleared() {
        webSocketRepository.disconnect()
        super.onCleared()
    }

    fun onInputChanged(input: String) {
        _state.update {
            it.copy(
                userInput = input
            )
        }
    }

    fun updateCode(code : String) {
        _state.update {
            it.copy(
                post = it.post.copy(
                    code = code
                )
            )
        }
    }

    fun updateTitle(title : String) {
        _state.update {
            it.copy(
                post = it.post.copy(
                    title = title
                )
            )
        }
    }

    fun updateContent(content : String) {
        _state.update {
            it.copy(
                post = it.post.copy(
                    description = content
                )
            )
        }
    }

    fun updateLanguage(language : CodeLang) {
        Timber.e("language ${language.name}")
        _state.update {
            it.copy(
                post = it.post.copy(
                    language = language.name
                )
            )
        }
    }

    fun removeTag(tag : String) {
        _state.update {
            it.copy(
                post = it.post.copy(
                    tags = it.post.tags.filter { it != tag }
                )
            )
        }
    }

    fun updateTag(tags : List<String>) {
        _state.update {
            it.copy(
                post = it.post.copy(
                    tags = tags
                )
            )
        }
    }

    fun updateComplete() {
        _state.update {
            it.copy(
                isComplete = true
            )
        }
    }

    fun clearData() {
        _state.value = PostState()
    }
}