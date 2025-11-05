package com.code.gram.domain.exception

import com.code.gram.domain.model.CodeError

class CodeRejectedException(
    val errorItems: List<CodeError>
) : Exception(
    errorItems.joinToString("\n") { "- ${it.loc.joinToString(".")} : ${it.msg}" }
)