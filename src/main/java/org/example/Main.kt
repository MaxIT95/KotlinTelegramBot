package org.example

import org.example.model.Question

const val MIN_COUNT_GOOD_ANSWER = 3
const val COUNT_VARIANTS = 4
const val SPLITTER = "|"
const val NEXT_LINE = "\n"

fun main() {

    val trainer = LearnWordsTrainer()

    while (true) {
        println(
            "Меню: \n" +
                    "1 – Учить слова\n" +
                    "2 – Статистика\n" +
                    "0 – Выход"
        )
        val input = readln()

        when (input) {
            "1" -> {
                while (true) {
                    val question = trainer.getNextQuestion()

                    if (question == null) {
                        println("Все слова в словаре выучены")
                        break
                    }
                    println("${question.correctAnswer.original}:")
                    question.printVariants()
                    val input = readln().toIntOrNull()
                    val isCorrectAnswer = trainer.checkAnswer(input)

                    if (isCorrectAnswer) {
                        println("Правильно! $NEXT_LINE")
                    } else {
                        println("Неправильно! ${question.correctAnswer.original} это ${question.correctAnswer.translate} $NEXT_LINE")
                    }

                }
            }

            "2" -> {
                val statistics = trainer.getStatistics()
                println(
                    "Выучено ${statistics.learnedCount} из ${statistics.allWordsCount} " +
                            "слов $SPLITTER ${statistics.percent}%\n"
                )
            }

            "0" -> break
            else -> {
                println("Введите число 1, 2 или 0")
            }
        }
    }
}

fun Question.printVariants() {
    variants.forEachIndexed { index, word ->
        println(" ${index + 1} - ${word.translate}\n")
    }
    println("----------\n0 - Меню")
}