package org.example

import java.io.File

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
            "2" -> println("Вы выбрали учить статистику")
            "0" -> break
            else -> {
                println("Введите число 1, 2 или 0")
            }
        }
    }
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