package y2023.day04

import Day

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(13, 30, 25231, 9721255) {

	fun parseLine(it: String): Pair<Set<String>, Set<String>> {
		val (_, win, have) = it.split(":", " |")
		val w = win.chunked(3).map { it }.toSet()
		val h = have.chunked(3).map { it }.toSet()
		return w to h
	}

	init {
		part1Lines { lines ->
			lines.map(::parseLine)
				.sumOf {
					val count = it.first.intersect(it.second).size
					if (count == 0) 0 else 1 shl (count - 1)
				}
		}

		data class CardInfo(val win: Int, var count: Int = 1)

		part2Lines { lines ->
			lines.map(::parseLine)
				.map {
					CardInfo(it.first.intersect(it.second).size)
				}
				.let { list ->
					list.forEachIndexed { index, card ->
						(index + 1..index + card.win).forEach {
							list[it].count += card.count
						}
					}
					list
				}
				.sumOf { it.count }
		}
	}
}