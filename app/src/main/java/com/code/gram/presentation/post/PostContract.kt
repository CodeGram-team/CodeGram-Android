package com.code.gram.presentation.post

import androidx.compose.runtime.Immutable
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class PostState(
    val tags : PersistentList<String> = persistentListOf(),
    val isLoading : Boolean = false,
    val code : String = "",
    val title : String = "",
    val content : String = "",
    val language: CodeLang = CodeLang.Java,
    val theme : String = "",
    val error : String = "",
    val type : String = ""
)
