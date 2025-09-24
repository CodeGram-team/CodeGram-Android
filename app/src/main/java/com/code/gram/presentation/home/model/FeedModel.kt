package com.code.gram.presentation.home.model

import androidx.compose.runtime.Immutable
import com.wakaztahir.codeeditor.highlight.model.CodeLang
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class FeedModel(
    val id: Int,
    val title: String,
    val code: String,
    val language: CodeLang,
    val tags: ImmutableList<String> = persistentListOf()
)