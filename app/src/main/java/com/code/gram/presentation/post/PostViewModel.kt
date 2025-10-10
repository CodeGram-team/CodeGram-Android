package com.code.gram.presentation.post

import androidx.lifecycle.ViewModel
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(

) : ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun updateCode(code : String) {
        _state.update {
            it.copy(code = code)
        }
    }

    fun updateTitle(title : String) {
        _state.update {
            it.copy(title = title)
        }
    }

    fun updateContent(content : String) {
        _state.update {
            it.copy(content = content)
        }
    }

    fun updateLanguage(language : CodeLang) {
        _state.update {
            it.copy(language = language)
        }
    }

    fun addTag(tag : String) {
        _state.update {
            it.copy(tags = it.tags.add(tag))
        }
    }

    fun removeTag(tag : String) {
        _state.update {
            it.copy(tags = it.tags.remove(tag))
        }
    }

    fun updateTag(tags : List<String>) {
        _state.update {
            it.copy(tags = tags.toPersistentList())
        }
    }
}