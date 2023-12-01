package days

import TxtReader

private const val txtPath: String = "day1.txt"
private val input: String = TxtReader.getStringFromTxt(txtPath)

private var result = 0

fun main() {
    addAllInts()
    println(result)
}

private fun addAllInts() = input.lines().forEach { result += addInt(it) }

private fun addInt(line: String): Int {
    val numbers = ArrayList<Int>()
    val arrayOfChars = line.toCharArray()

    for ((i, letter) in arrayOfChars.withIndex()) {
        if (Character.isDigit(letter)) {
            numbers.add(letter.toString().toInt())
        } else {
            getIntFromText(String(arrayOfChars).substring(i)).apply {
                if (this != 0) numbers.add(this)
            }
        }
    }

    return "${numbers.first()}${numbers.last()}".toInt()
}

private fun getIntFromText(textToCheck: String): Int {
    val size = textToCheck.length
    var number = 0

    if (size >= 5) {
        val text3 = textToCheck.substring(0, 3)
        val text4 = textToCheck.substring(0, 4)
        val text5 = textToCheck.substring(0, 5)

        listOf(text3, text4, text5).forEach { text ->
            getIntByName(text).takeIf { result -> result != 0 }?.let { number = it }
        }
    } else if (size == 4) {
        val text3 = textToCheck.substring(0, 3)
        val text4 = textToCheck.substring(0, 4)

        listOf(text3, text4).forEach { text ->
            getIntByName(text).takeIf { result -> result != 0 }?.let { number = it }
        }
    } else if (size == 3) {
        val text3 = textToCheck.substring(0, 3)

        getIntByName(text3).takeIf { it != 0 }?.let { number = it }
    }

    return number
}

private fun getIntByName(word: String): Int =
    when (word) {
        "one" -> 1
        "two" -> 2
        "three" -> 3
        "four" -> 4
        "five" -> 5
        "six" -> 6
        "seven" -> 7
        "eight" -> 8
        "nine" -> 9
        else -> 0
    }