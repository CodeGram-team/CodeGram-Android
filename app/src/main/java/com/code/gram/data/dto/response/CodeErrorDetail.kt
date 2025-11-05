package com.code.gram.data.dto.response

import com.code.gram.domain.model.CodeError
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class CodeErrorItem(
    val type: String,
    override val loc: List<String>,
    override val msg: String,
    val input: String? = null,
    val ctx: JsonObject? = null
) : CodeError