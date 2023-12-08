package day2


import java.io.File

val lines: List<String> = File("src/day2/day2.txt").readLines()
val games: List<Game> = lines.map { Game(it) }

fun main() {
    println(part1())
    println(part2())
}

fun part1(): Int {
    val availableReds =12
    val availableGreens = 13
    val availableBlues = 14

    val idSum: Int = games
            .filter { it.isPossible(availableReds, availableGreens, availableBlues) }
            .sumOf { it.gameId }

    return idSum
}

fun part2(): Int {
    val totalPower: Int = games.sumOf{ it.power}
    return totalPower
}

data class Game(
    val gameId: Int,
    val moveSet: List<CubeSet>
)

fun Game(line: String): Game {
    val gameId: Int = line.removePrefix("Game ").takeWhile { it.isDigit() }.toInt()
    val allVisibleCubesText: String = line.removeRegexPrefix("""Game \d+: """.toRegex())
    val cubeSets: List<CubeSet> = allVisibleCubesText
            .split(";")
            .map { CubeSet(it.trim()) }
    return Game(gameId, cubeSets)
}

fun Game.isPossible(availableReds: Int, availableGreens: Int, availableBlues: Int): Boolean {
   return moveSet.all {
       it.red <= availableReds && it.green <= availableGreens && it.blue <= availableBlues
   }
}

val Game.power: Int
    get() {
        val requiredReds: Int = moveSet.maxOf {it.red}
        val requiredGreens: Int = moveSet.maxOf {it.green}
        val requiredBlues: Int = moveSet.maxOf {it.blue}
        return requiredReds * requiredGreens * requiredBlues
    }

data class CubeSet(
    val red: Int,
    val green: Int,
    val blue: Int
)

fun CubeSet(string: String): CubeSet {
    val individualCubes: Map<Color, Int> = string
            .split(", ")
            .associate { numberToColorText: String ->
                val (countStr: String, colorName: String) = numberToColorText.split(" ")
                val color: Color = Color.entries.first {it.text == colorName}
                val count: Int = countStr.toInt()
                color to count
            }
    return CubeSet(
            individualCubes[Color.RED] ?: 0,
            individualCubes[Color.GREEN] ?: 0,
            individualCubes[Color.BLUE] ?: 0
    )
}

enum class Color(val text: String) {
    RED("red"),
    GREEN("green"),
    BLUE("blue")
}

fun String.removeRegexPrefix(re: Regex): String {
    val match: MatchResult = re.find(this) ?: return this
    return if (match.range.first ==0) this.removeRange(match.range) else this
}