package y2022.day01

import Day


fun main() {
	AOC
}

private object AOC : Day<Int, Int>(24000, 45000, 72017, 212520) {

	fun buildList(txt: String): List<Int> = txt
		.split("\n\n")
		.map {
			it.lines()
				.sumOf { line ->
					line.toInt()
				}
		}

	init {
		part1Text { buildList(it).max() }

		part2Text { buildList(it).sortedDescending().take(3).sum() }
	}
}