package com.code.gram.data.dto.request

import com.code.gram.domain.entity.home.CodeRequestEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CodeRequest(
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("language")
    val language: String,
    @SerialName("code")
    val code: String,
    @SerialName("tags")
    val tags: List<String>
)

fun CodeRequestEntity.toData() = CodeRequest(
    title = title,
    description = description,
    language = language,
    code = code,
    tags = tags
)
