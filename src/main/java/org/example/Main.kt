package org.example

import java.io.File
import kotlin.math.PI

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
    val alreadyLearnedList = dictionary.filter { it.correctAnswersCount > MIN_COUNT_GOOD_ANSWER }
    val notLearnedList = dictionary.filter { it.correctAnswersCount < MIN_COUNT_GOOD_ANSWER }
    var questionWords: MutableList<Word>

    while (true) {
        if (notLearnedList.isEmpty()) {
            println("Все слова в словаре выучены")
            break
        }
        questionWords = notLearnedList.take(4).shuffled().toMutableList()
        validateResponseCount(questionWords, alreadyLearnedList)
        val correctAnswer = questionWords.random()

        println(
            "${correctAnswer.original}:\n" +
                    " 1 - ${questionWords.get(0).translate}\n" +
                    " 2 - ${questionWords.get(1).translate}\n" +
                    " 3 - ${questionWords.get(2).translate}\n" +
                    " 4 - ${questionWords.get(3).translate}"
        )
        val inputAnswer = readln().toInt()

        if (questionWords[inputAnswer - 1] == correctAnswer) {
            println("Верный ответ!")
            break
        }
    }
}

fun validateResponseCount(responses: MutableList<Word>, learnedWords: List<Word>) {
    while (responses.size < 4) {
        val word = learnedWords.random();
        if (responses.contains(word)) {
            continue
        }
        responses.add(word)
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