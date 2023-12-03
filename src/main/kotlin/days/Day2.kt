package days

import TxtReader
import kotlin.math.max

private const val txtPath: String = "day2.txt"
private val input: String = TxtReader.getStringFromTxt(txtPath)

private var result1 = 0
private var result2 = 0

fun main() {
    setResults()
    println("result 1: $result1")
    println("result 2: $result2")
}

private fun setResults() = input.lines().forEachIndexed { index, line ->
    if (isIndexValid(line)) result1 += index + 1
    result2 += getValue(line)
}

private fun isIndexValid(line: String): Boolean {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14

    val newText = line.substringAfter(':').trim()
    val arrayOfSets = newText.split(';')
    var isValid = true

    for (set in arrayOfSets) {
        var numberOfBlue = 0
        var numberOfRed = 0
        var numberOfGreen = 0

        var i = 0
        for (letter in set) {
            if (letter != set[i]) continue
            if (Character.isDigit(letter)) {
                val color = getColorByIndex(set, i)
                i++

                when (color) {
                    Colors.BLUE -> numberOfBlue += "${letter}${set[i]}".trim().toInt()
                    Colors.RED -> numberOfRed += "${letter}${set[i]}".trim().toInt()
                    Colors.GREEN -> numberOfGreen += "${letter}${set[i]}".trim().toInt()
                    else -> {}
                }
            }
            i++
        }
        if (numberOfRed > maxRed || numberOfGreen > maxGreen || numberOfBlue > maxBlue) isValid = false
    }

    return isValid
}

private fun getValue(line: String): Int {
    val newText = line.substringAfter(':').trim()
    var numberOfBlue: Int? = null
    var numberOfRed: Int? = null
    var numberOfGreen: Int? = null

    var i = 0
    for (letter in newText) {
        if (letter != newText[i]) continue
        if (Character.isDigit(letter)) {
            val color = getColorByIndex(newText, i)
            i++

            when (color) {
                Colors.BLUE -> numberOfBlue = numberOfBlue?.let { max(it, "${letter}${newText[i]}".trim().toInt()) }
                    ?: "${letter}${newText[i]}".trim().toInt()

                Colors.RED -> numberOfRed = numberOfRed?.let { max(it, "${letter}${newText[i]}".trim().toInt()) }
                    ?: "${letter}${newText[i]}".trim().toInt()

                Colors.GREEN -> numberOfGreen = numberOfGreen?.let { max(it, "${letter}${newText[i]}".trim().toInt()) }
                    ?: "${letter}${newText[i]}".trim().toInt()

                else -> {}
            }
        }
        i++
    }

    return numberOfBlue!! * numberOfRed!! * numberOfGreen!!
}

private fun getColorByIndex(text: String, i: Int) = if (text[i + 2].isLetter()) {
    Colors.fromLetter(text[i + 2])
} else { //(newText[i+3].isLetter())
    Colors.fromLetter(text[i + 3])
}

private enum class Colors(val letter: Char) {
    RED('r'),
    GREEN('g'),
    BLUE('b'),
    NONE(' ');

    companion object {
        fun fromLetter(letter: Char) = values().firstOrNull { it.letter == letter } ?: NONE
    }
}