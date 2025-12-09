package org.example

import java.io.File

fun main() {
    val file = File("src/main/resources/words.txt")

    var dictionaries = mutableListOf<Word>()
    val lines = file.readLines()

    lines.forEach {
        val line = it.split("|")
        val word = Word(line[0], line[1])

        if (line[2].isNotEmpty()) {
            word.correctAnswersCount = line[2].toInt()
        }
        dictionaries.add(word)
    }
    println(dictionaries)
}