package com.code.gram.data.util

import com.code.gram.data.dto.response.CodeErrorItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

fun parseErrorArray(jsonString: String): List<CodeErrorItem> {
    val json = Json { ignoreUnknownKeys = true }

    val root = Json.parseToJsonElement(jsonString).jsonObject
    val detailArray = root["detail"]?.toString() ?: "[]"

    return json.decodeFromString(detailArray)
}