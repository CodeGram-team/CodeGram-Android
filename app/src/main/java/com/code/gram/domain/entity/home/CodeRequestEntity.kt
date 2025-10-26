package com.code.gram.domain.entity.home

data class CodeRequestEntity (
    val title: String,
    val description: String,
    val language: String,
    val code: String,
    val tags: List<String>
)