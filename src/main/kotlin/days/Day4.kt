package days

import TxtReader
import kotlin.math.pow

private const val txtPath: String = "day4.txt"
private val input: String = TxtReader.getStringFromTxt(txtPath)

private var result1 = 0
private var result2 = 0

fun main() {
    Day4.setResults()
    println("result 1: $result1")
    println("result 2: $result2")
}

object Day4 {
    fun setResults() {
        result1 += Exercise1.getValue(input.lines())
        result2 = Exercise2.getValue(input.lines())
    }

    private object Exercise1 {
        fun getValue(lines: List<String>): Int {
            var result = 0
            val cards = getArrayListOfCards(lines)
            val validNumbers = getValidNumbers(cards)

            for (quantityOfValidNumber in validNumbers) {
                quantityOfValidNumber.apply {
                    if (this > 0) {
                        result += (2.0).pow(this - 1).toInt()
                    }
                }
            }

            return result
        }

        fun getArrayListOfCards(lines: List<String>): ArrayList<Card> {
            val cards = ArrayList<Card>()

            for (line in lines) {
                val separatedText = line.substringAfter(':').split('|')
                val winningNumbersText = separatedText[0].trim()
                val myNumbersText = separatedText[1].trim()

                val card = Card(
                    getArrayFromText(winningNumbersText),
                    getArrayFromText(myNumbersText)
                )

                cards.add(card)
            }

            return cards
        }

        fun getValidNumbers(cards: ArrayList<Card>): MutableList<Int> {
            val validNumbers = mutableListOf<Int>()

            for (card in cards) {
                validNumbers.add(0)

                for (number in card.winningNumbers) {
                    if (isValid(number, card.myNumbers)) {
                        validNumbers.apply {
                            this[this.lastIndex] = this.last() + 1
                        }
                    }
                }
            }

            return validNumbers
        }

        private fun getArrayFromText(winningNumbersText: String): ArrayList<Int> {
            val arrayOfStrings = winningNumbersText.split(' ')
            val arrayOfInts = ArrayList<Int>()

            for (numberInString in arrayOfStrings) {
                numberInString.apply {
                    if (this.firstOrNull() != ' ' && this.firstOrNull() != null) arrayOfInts.add(this.toInt())
                }
            }

            return arrayOfInts
        }

        private fun isValid(number: Int, myNumbersArray: ArrayList<Int>): Boolean {
            for (myNumber in myNumbersArray) {
                if (myNumber == number) return true
            }

            return false
        }

        data class Card(val winningNumbers: ArrayList<Int>, val myNumbers: ArrayList<Int>)
    }

    private object Exercise2 {
        fun getValue(lines: List<String>): Int {
            var result = 0
            val cards = Exercise1.getArrayListOfCards(lines)
            val validNumbers = Exercise1.getValidNumbers(cards)
            val cardsQuantity = mutableListOf<Int>()

            for (line in lines) cardsQuantity.add(0)

            for ((i, quantityOfValidNumbers) in validNumbers.withIndex()) {
                result++
                for (j in 0..cardsQuantity[i]) {
                    val min = i + 1
                    val max = if (i + quantityOfValidNumbers < validNumbers.size - 1) {
                        i + quantityOfValidNumbers
                    } else {
                        validNumbers.size - 1
                    }

                    for (k in min..max) {
                        cardsQuantity.apply {
                            this[k] = this[k] + 1
                        }
                        result++
                    }
                }
            }

            return result
        }
    }
}