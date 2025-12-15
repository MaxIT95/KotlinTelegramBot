package org.example

import java.io.File

const val MIN_COUNT_GOOD_ANSWER = 3
const val COUNT_VARIANTS = 4

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
            "1" -> learnWords(dictionary)
            "2" -> println(getStatistic(dictionary))
            "0" -> break
            else -> {
                println("Введите число 1, 2 или 0")
            }
        }
    }
}

fun learnWords(dictionary: List<Word>) {
    while (true) {
        val notLearnedList = dictionary.filter { it.correctAnswersCount <= MIN_COUNT_GOOD_ANSWER }
        if (notLearnedList.isEmpty()) {
            println("Все слова в словаре выучены")
            break
        }
        var variants = notLearnedList.shuffled().take(COUNT_VARIANTS)

        val correctAnswer = variants.random()
        val questionWords = if (variants.size < COUNT_VARIANTS) {
            val learnedList = dictionary.filter { it.correctAnswersCount >= MIN_COUNT_GOOD_ANSWER }.shuffled()
            variants + learnedList.take(COUNT_VARIANTS - notLearnedList.size)
        } else {
            variants
        }.shuffled()
        println("${correctAnswer.original}:")
        questionWords.forEachIndexed { index, word ->
            println(" ${index + 1} - ${word.translate}\n")
        }

        val inputAnswer = readln().toInt()

        if (questionWords[inputAnswer - 1] == correctAnswer) {
            println("Верный ответ!")
            break
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
    return dictionaries
}