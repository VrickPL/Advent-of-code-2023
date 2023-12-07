package days

import TxtReader

private const val txtPath: String = "day6.txt"
private val input: String = TxtReader.getStringFromTxt(txtPath)

private var result1 = 0
private var result2 = 0

fun main() {
    Day6.setResults()
    println("result 1: $result1")
    println("result 2: $result2")
}

object Day6 {
    fun setResults() {
        result1 += Exercise1.getValue(input.lines())
        result2 = Exercise2.getValue(input.lines())
    }

    private object Exercise1 {
        fun getValue(lines: List<String>): Int {
            var result = 0
            val races = getArrayOfRaces(lines)

            for (race in races) {
                getResultFromRace(race).apply {
                    if (result == 0 && this != 0) {
                        result = 1
                    }
                    result *= this
                }

            }

            return result
        }

        private fun getArrayOfRaces(lines: List<String>): ArrayList<Race> {
            val races = ArrayList<Race>()
            var times = ArrayList<Int>()
            var distances = ArrayList<Int>()

            for ((i, line) in lines.withIndex()) {
                val stringWithValues: String = line.substringAfter(':').trim()

                if(i == 0) {
                    times = getArrayOfValues(stringWithValues)
                } else {
                    distances = getArrayOfValues(stringWithValues)
                }
            }

            for (i in 0 until times.size) {
                races.add(Race(times[i].toLong(), distances[i].toLong()))
            }

            return races
        }

        fun getArrayOfValues(line: String): ArrayList<Int> {
            val arrayOfStrings = line.split(' ')
            val arrayOfInts = ArrayList<Int>()

            for (numberInString in arrayOfStrings) {
                numberInString.apply {
                    if (this.firstOrNull() != ' ' && this.firstOrNull() != null) arrayOfInts.add(this.toInt())
                }
            }

            return arrayOfInts
        }

        fun getResultFromRace(race: Race): Int {
            var result = 0

            race.time.apply {
                for (i in this downTo 0) {
                    if (isRaceOk(this, race.distance, i)) result++
                }
            }

            return result
        }

        private fun isRaceOk(time: Long, distance: Long, index: Long): Boolean {
            val holdTime = time - index

            val myDistance = holdTime * index

            return myDistance > distance
        }
    }

    private object Exercise2 {
        fun getValue(lines: List<String>): Int {
            var result = 0
            val races = getArrayOfRaces(lines)

            for (race in races) {
                Exercise1.getResultFromRace(race).apply {
                    if (result == 0 && this != 0) {
                        result = 1
                    }
                    result *= this
                }

            }

            return result
        }

        private fun getArrayOfRaces(lines: List<String>): ArrayList<Race> {
            val races = ArrayList<Race>()
            var times = ArrayList<Int>()
            var distances = ArrayList<Int>()

            for ((i, line) in lines.withIndex()) {
                val stringWithValues: String = line.substringAfter(':').trim()

                if(i == 0) {
                    times = Exercise1.getArrayOfValues(stringWithValues)
                } else {
                    distances = Exercise1.getArrayOfValues(stringWithValues)
                }
            }
            var time = ""
            var distance = ""

            for (i in 0 until times.size) {
                time += times[i]
                distance += distances[i]
            }
            races.add(Race(time.toLong(), distance.toLong()))

            return races
        }
    }

    private data class Race(val time: Long, val distance: Long)
}