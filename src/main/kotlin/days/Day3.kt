package days

import TxtReader
import java.math.BigInteger

private const val txtPath: String = "day3.txt"
private val input: String = TxtReader.getStringFromTxt(txtPath)

object Day3 {
    private var result1 = 0
    private var result2 = BigInteger.ZERO

    fun main() {
        setResults()
        println("result 1: $result1")
        println("result 2: $result2")
    }

    private fun setResults() {
        result1 += Exercise1.getValue(input.lines())
        result2 = Exercise2.getValue(input.lines())
    }

    private object Exercise1 {
        fun getValue(lines: List<String>): Int {
            var result = 0
            val numberOfLines = lines.size - 1
            val listToPass = ArrayList<String>()

            for (i in 0..numberOfLines) {
                listToPass.add(lines[i])
                when (i) {
                    0 -> continue
                    1 -> result += getValueBySetOfLines(listToPass, Lines.FIRST)
                    else -> {
                        result += getValueBySetOfLines(listToPass, Lines.NORMAL)
                        listToPass.removeAt(0)

                        if (i == numberOfLines) result += getValueBySetOfLines(listToPass, Lines.LAST)
                    }
                }
            }

            return result
        }

        private fun getValueBySetOfLines(list: ArrayList<String>, lineName: Lines): Int {
            var result = 0
            val line = when (lineName) {
                Lines.FIRST -> list[0]
                Lines.NORMAL -> list[1]
                Lines.LAST -> list[1]
            }
            var numberToMiss = 0

            for ((i, letter) in line.withIndex()) {
                if (numberToMiss > 0) {
                    numberToMiss--
                    continue
                }
                if (letter.isDigit()) {
                    val number = getNumber(line, i)
                    numberToMiss = number.toString().length - 1
                    if (isValid(list, i, number, lineName)) result += number
                }
            }

            return result
        }

        private fun getNumber(line: String, i: Int): Int {
            var j = i
            var result = ""
            while (j < line.length && line[j].isDigit()) {
                result += "${line[j]}"
                j++
            }
            return result.trim().toInt()
        }

        private fun isValid(lines: ArrayList<String>, i: Int, number: Int, lineName: Lines): Boolean {
            var isValid: Boolean
            val lengthOfNumber = number.toString().length
            val lengthOfLine = lines[0].length - 1
            val line = lineName.getLine(lines)
            val lineUnder: String? = lineName.getLineUnder(lines)
            val lineAbove: String? = lineName.getLineAbove(lines)

            isValid = isLeftAndRightValid(i, lengthOfNumber, lengthOfLine, line)
            lineAbove?.let {
                if (!isValid) isValid = isLineValid(i, lengthOfNumber, lengthOfLine, it)
            }
            lineUnder?.let {
                if (!isValid) isValid = isLineValid(i, lengthOfNumber, lengthOfLine, it)
            }

            return isValid
        }

        private fun isLeftAndRightValid(
            i: Int,
            lengthOfNumber: Int,
            lengthOfLine: Int,
            line: String
        ): Boolean {
            var isValid = false

            if (i > 0) { //left
                if (isItSign(line[i - 1])) isValid = true
            }
            if (i + lengthOfNumber < lengthOfLine && !isValid) { //right
                if (isItSign(line[lengthOfNumber + i])) isValid = true
            }

            return isValid
        }

        private fun isLineValid(
            i: Int,
            lengthOfNumber: Int,
            lengthOfLine: Int,
            lineToCheck: String
        ): Boolean {
            var isValid = false
            var j = if (i != 0) i - 1 else 0
            val maxNumber = if (i + lengthOfNumber < lengthOfLine - 1) {
                i + lengthOfNumber
            } else {
                i + lengthOfNumber - 1
            }
            while (j <= maxNumber && !isValid) { //line under
                isValid = isItSign(lineToCheck[j])
                j++
            }

            return isValid
        }

        private fun isItSign(letter: Char): Boolean = (!letter.isDigit() && letter != '.')

        private enum class Lines {
            FIRST {
                override fun getLine(lines: List<String>): String = lines[0]
                override fun getLineAbove(lines: List<String>): String? = null
                override fun getLineUnder(lines: List<String>): String = lines[1]
            },
            NORMAL {
                override fun getLine(lines: List<String>): String = lines[1]
                override fun getLineAbove(lines: List<String>): String = lines[0]
                override fun getLineUnder(lines: List<String>): String = lines[1]
            },
            LAST {
                override fun getLine(lines: List<String>): String = lines[1]
                override fun getLineAbove(lines: List<String>): String = lines[0]
                override fun getLineUnder(lines: List<String>): String? = null
            };

            abstract fun getLine(lines: List<String>): String
            abstract fun getLineAbove(lines: List<String>): String?
            abstract fun getLineUnder(lines: List<String>): String?
        }
    }

    private object Exercise2 {
        fun getValue(lines: List<String>): BigInteger {
            var result = BigInteger.ZERO
            val numberOfLines = lines.size - 1
            val listToPass = ArrayList<String>()

            for (i in 0..numberOfLines) {
                listToPass.add(lines[i])
                if (i == 0 || i == 1) {
                    continue
                } else {
                    result += BigInteger.valueOf(getValueBySetOfLines(listToPass).toLong())
                    listToPass.removeAt(0)
                }
            }

            return result
        }

        private fun getValueBySetOfLines(list: ArrayList<String>): Int {
            var result = 0
            val line = list[1]

            for ((i, letter) in line.withIndex()) {
                if (letter == '*') {
                    result += getRatio(list, i)
                }
            }

            return result
        }

        private fun getRatio(lines: ArrayList<String>, i: Int): Int {
            var result = 0
            val numbers = ArrayList<Int>()
            val line = lines[1]

            val min = if (i > 0) i - 1 else 0
            val max = if (i == line.length - 1 || i == line.length - 2) i else i + 1

            for (lineToCheck in lines) {
                var numberToMiss = 0
                for (j in min..max) {
                    if (numberToMiss > 0) {
                        numberToMiss--
                        continue
                    }
                    if (lineToCheck[j].isDigit()) {
                        numbers.add(getNumber(j, lineToCheck))
                        for (k in j..max) {
                            if (lineToCheck[k].isDigit()) {
                                numberToMiss++
                            } else {
                                break
                            }

                        }
                    }
                }
            }
            if (numbers.size == 2) {
                result = 1
                numbers.forEach { number -> result *= number }
            }

            return result
        }

        private fun getNumber(i: Int, line: String): Int {
            var result = ""
            val lengthOfLine = line.length - 1
            val charArray = ArrayList<Char>()

            if (i > 1) {
                if (line[i - 1].isDigit()) {
                    if (line[i - 2].isDigit()) {
                        charArray.add(line[i - 2])
                    }
                    charArray.add(line[i - 1])
                }
            } else if (i == 1) {
                if (line[0].isDigit()) {
                    charArray.add(line[0])
                }
            }
            charArray.add(line[i])
            if (i <= lengthOfLine - 2) {
                if (line[i + 1].isDigit()) {
                    charArray.add(line[i + 1])
                    if (line[i + 2].isDigit()) {
                        charArray.add(line[i + 2])
                    }
                }
            } else if (i == lengthOfLine - 1) {
                if (line[i + 1].isDigit()) {
                    charArray.add(line[i + 1])
                }
            }

            for (char in charArray) result += "$char"

            return result.toInt()
        }
    }
}