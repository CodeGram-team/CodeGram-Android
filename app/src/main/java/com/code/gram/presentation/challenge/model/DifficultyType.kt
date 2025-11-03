package com.code.gram.presentation.challenge.model

enum class DifficultyType(
    val label: String
) {
    INTRODUCTORY("EASY"),
    COMPETITION("MEDIUM"),
    INTERVIEW("HARD");

    companion object {
        fun fromLabel(label: String): DifficultyType {
            return entries.find { it.name == label.uppercase() } ?: INTRODUCTORY
        }
    }
}