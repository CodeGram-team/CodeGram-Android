package com.code.gram.presentation.challenge.detail.state

import com.code.gram.presentation.challenge.detail.model.ChallengeResultUiModel
import com.code.gram.presentation.challenge.detail.model.ProblemDetailUiModel
import com.wakaztahir.codeeditor.highlight.model.CodeLang

data class ChallengeDetailState(
    val problemDetailUiModel: ProblemDetailUiModel? = null,
    val language: String = "",
    val code: String = "",
    val challengeResult : ChallengeResultUiModel? = null
) {
    val codeLang: CodeLang?
        get() = CodeLang.entries.firstOrNull { lang ->
            lang.value.any { it.equals(language, ignoreCase = true) }
        }
}