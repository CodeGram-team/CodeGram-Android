package com.code.gram.presentation.post

import androidx.compose.runtime.Immutable
import com.code.gram.presentation.post.model.PostDataUiModel
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class PostState(
    val isLoading : Boolean = false,
    val isSuccess : Boolean = false,
    val isComplete : Boolean = false,
    val theme : String = "",
    val error : String = "",
    val errorReasons : List<String> = persistentListOf(),
    val type : String = "",

    val codeResult : String = "",
    val userInput : String = "",
    val post : PostDataUiModel = PostDataUiModel(),
)
