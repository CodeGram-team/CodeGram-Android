package com.code.gram.presentation.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                    language = _state.value.post.language,
                    tags = _state.value.post.tags
                ).toEntity()
            ).onSuccess {
                Timber.e("success postCode")
            }.onFailure {
                Timber.e(it.message.toString())
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
                        codeResult = "[${result.type}] ${result.data}"
                    )
                }
                Timber.e("result ${result}")
            }
        }
    }

    fun sendInput(input: String) {
        webSocketRepository.sendInput(input)
    }

    override fun onCleared() {
        webSocketRepository.disconnect()
        super.onCleared()
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
}