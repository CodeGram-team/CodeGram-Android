package com.code.gram.domain.model

interface CodeError {
    val loc: List<String>
    val msg: String
}