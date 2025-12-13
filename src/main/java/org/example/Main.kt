package org.example

import java.io.File

const val MIN_COUNT_GOOD_ANSWER = 3

fun main() {
    val file = File("src/main/resources/words.txt")
    val dictionary = loadDictionary(file)

    while (true) {
        println(
            "Меню: \n" +
                    "1 – Учить слова\n" +
                    "2 – Статистика\n" +
                    "0 – Выход"
        )
        val input = readln()

        when (input) {
            "1" -> println("Вы выбрали учить слова")
            "2" -> println(getStatistic(dictionary))
            "0" -> break
            else -> {
                println("Введите число 1, 2 или 0")
            }
        }
    }
}

fun getStatistic(words: List<Word>): String {
    val allWordsCount = words.size
    val learnedCount = words.filter { it.correctAnswersCount >= MIN_COUNT_GOOD_ANSWER }.size
    var percent = 0

    if (allWordsCount > 0) {
        percent = (learnedCount * 100) / allWordsCount
    }
    return "Выучено $learnedCount из $allWordsCount слов | $percent%\n"
}

fun loadDictionary(file: File): List<Word> {
    val dictionaries = mutableListOf<Word>()
    val lines = file.readLines()

    lines.forEach {
        val line = it.split("|")
        val word = Word(line[0], line[1])

        if (line[2].isNotEmpty()) {
            word.correctAnswersCount = line[2].toInt()
        }
        dictionaries.add(word)
    }
    return dictionaries;
}