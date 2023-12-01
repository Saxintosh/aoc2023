package day01

import DayList


fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>( 142, 281) {

	val words = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine") + (1..9).map { "$it" }

	override fun part1(lines: List<String>): Int = lines.sumOf { line ->
		val digits = line.filter { it.isDigit() }
		(digits.first().toString() + digits.last()).toInt()
	}

	private fun Pair<Int, String>.toInt() = if (second.length == 1) second.toInt() else words.indexOf(second) + 1

	override fun part2(lines: List<String>): Int = lines.sumOf { line ->
		val first = line.findAnyOf(words)?.toInt() ?: throw UnknownError()
		val last = line.findLastAnyOf(words)?.toInt() ?: throw UnknownError()

		first * 10 + last
	}

}