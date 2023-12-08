import java.io.File

fun main () {
    val d = Day1(File("src/day1/day1.txt").readLines())
    println(d.solvePart1())
    println(d.solvePart1Long())
    println(d.solvePart2())
}

class Day1 (private val input: List<String>) {

    private val words: Map<String, Int> = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
    fun solvePart1(): Int =
        input.sumOf {calibrationValue(it)}

    fun solvePart1Long(): Int =
        input.sumOf {calibrationValueLong(it)}

    fun solvePart2(): Int =
        input.sumOf { row ->
            calibrationValue(
                row.mapIndexedNotNull { index, c ->
                    if (c.isDigit()) c
                    else
                        row.possibleWordsAt(index).firstNotNullOfOrNull { candidate ->
                            words[candidate]
                        }
                }.joinToString()
            )
        }

    private fun calibrationValue (row: String): Int =
        "${row.first {it.isDigit() }}${row.last {it.isDigit()}}".toInt()

    private fun calibrationValueLong (row: String): Int {
        val firstDigit = row.first {it.isDigit() }
        val lastDigit = row.last {it.isDigit() }
        return "$firstDigit$lastDigit".toInt()
    }

    private fun String.possibleWordsAt(startingAt: Int): List<String> =
        (3..5).map { len ->
            substring(startingAt, (startingAt + len).coerceAtMost(length))
        }

}