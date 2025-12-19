package org.example.model

import org.example.model.Word

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word
)