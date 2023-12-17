package y2023.day01

import Day


fun main() {
	AOC2
}

private object AOC2 : Day<Int, Int>(142, 281, 54304, 54418) {

	val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine") + (1..9).map { "$it" }

	fun Pair<Int, String>.toInt() = if (second.length == 1) second.toInt() else words.indexOf(second) + 1

	init {
		part1Lines { lines ->
			lines.sumOf { line ->
				val digits = line.filter { it.isDigit() }
				(digits.first().toString() + digits.last()).toInt()
			}
		}

		part2Lines { lines ->
			lines.sumOf { line ->
				val first = line.findAnyOf(words)?.toInt() ?: throw UnknownError()
				val last = line.findLastAnyOf(words)?.toInt() ?: throw UnknownError()

				first * 10 + last
			}
		}
	}
}