package org.example.model

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word
)