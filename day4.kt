import java.io.File

fun convertRanges(input: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> = 
    input.map { it.split(",") }
        .map { Pair(convertRange(it[0]), convertRange(it[1])) }

fun convertRange(input: String): Pair<Int, Int> {
  val values = input.split("-")
  return Pair(values[0].toInt(), values[1].toInt())
}

fun insiderRanges(ranges: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Int =
    ranges.count {
        (it.first.first <= it.second.first && it.first.second >= it.second.second) ||
        (it.first.first >= it.second.first && it.first.second <= it.second.second)
    }

fun niceLaps(ranges: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>) =
    ranges.count {
        (it.first.first <= it.second.first && it.first.second >= it.second.second) ||
        (it.first.first >= it.second.first && it.first.second <= it.second.second) ||
        (it.first.first <= it.second.first && it.first.second >= it.second.first && it.first.second <= it.second.second) ||
        (it.second.first <= it.first.first && it.second.second >= it.first.first && it.second.second <= it.first.second)
    }

fun main() {
    val input = File("./input/day4.txt").readLines()
    val ranges = convertRanges(input)
    val insiderCount = insiderRanges(ranges)
    println("Insider Ranges are $insiderCount")

    val lapsCount = niceLaps(ranges)
    println("Those Laps are $lapsCount")
}
