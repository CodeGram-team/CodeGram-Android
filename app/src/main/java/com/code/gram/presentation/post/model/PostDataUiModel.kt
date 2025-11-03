package com.code.gram.presentation.post.model

import com.code.gram.domain.entity.home.CodeRequestEntity
import com.wakaztahir.codeeditor.highlight.model.CodeLang

data class PostDataUiModel(
    val title: String = "",
    val description: String = "",
    val code: String = "",
    val language: String = "",
    val tags: List<String> = emptyList(),
) {
    val codeLang: CodeLang?
        get() = CodeLang.entries.firstOrNull { lang ->
            lang.value.any { it.equals(language, ignoreCase = true) }
        }
}

fun PostDataUiModel.toEntity() = CodeRequestEntity(
    title = title,
    description = description,
    code = code,
    language = language,
    tags = tags
)
