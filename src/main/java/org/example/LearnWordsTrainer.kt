package org.example

import org.example.model.Question
import org.example.model.Statistics
import org.example.model.Word
import java.io.File

const val DICT_PATH = "src/main/resources/words.txt"

class LearnWordsTrainer {

    val dictionary = loadDictionary()
    private var question: Question? = null

    fun saveDictionary(dictionary: List<Word>) {
        val file = File(DICT_PATH)
        val newDictionary = StringBuilder()

        dictionary.forEach {
            newDictionary.append(it.original)
            newDictionary.append(SPLITTER)
            newDictionary.append(it.translate)
            newDictionary.append(SPLITTER)
            newDictionary.append(it.correctAnswersCount)
            newDictionary.append(NEXT_LINE)
        }
        file.writeText(newDictionary.toString())
    }

    fun getStatistics(): Statistics {
        val allWordsCount = dictionary.size
        val learnedCount = dictionary.filter { it.correctAnswersCount >= MIN_COUNT_GOOD_ANSWER }.size
        var percent = 0

        if (allWordsCount > 0) {
            percent = (learnedCount * 100) / allWordsCount
        }
        return Statistics(allWordsCount, learnedCount, percent)
    }

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { it.correctAnswersCount <= MIN_COUNT_GOOD_ANSWER }

        if (notLearnedList.isEmpty()) {
            return null
        }
        val variants = notLearnedList.shuffled().take(COUNT_VARIANTS)

        val correctAnswer = variants.random()
        val questionWords = prepareVariants(dictionary, notLearnedList.size)

        question = Question(questionWords, correctAnswer)
        return question
    }

    fun checkAnswer(inputAnswerIndex: Int?): Boolean {

        return question?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (inputAnswerIndex == correctAnswerId) {
                it.correctAnswer.correctAnswersCount++
                saveDictionary(dictionary)
                true
            } else {
                false
            }
        } ?: false
    }

    private fun loadDictionary(): List<Word> {
        val file = File(DICT_PATH)
        val dictionaries = mutableListOf<Word>()
        val lines = file.readLines()

        lines.forEach {
            val line = it.split(SPLITTER)
            val word = Word(line[0], line[1])

            if (line[2].isNotEmpty()) {
                word.correctAnswersCount = line[2].toInt()
            }
            dictionaries.add(word)
        }
        return dictionaries
    }

    private fun prepareVariants(dictionary: List<Word>, currentVariantsSize: Int): List<Word> {
        val variants = dictionary.shuffled().take(COUNT_VARIANTS)

        val questionWords = if (variants.size < COUNT_VARIANTS) {
            val learnedList = dictionary.filter { it.correctAnswersCount >= MIN_COUNT_GOOD_ANSWER }.shuffled()
            variants + learnedList.take(COUNT_VARIANTS - currentVariantsSize)
        } else {
            variants
        }.shuffled()

        return questionWords
    }
}