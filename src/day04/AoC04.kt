package day04

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(13, 30, 25231, 9721255) {

	private fun parseLine(it: String): Pair<Set<String>, Set<String>> {
		val (_, win, have) = it.split(":", " |")
		val w = win.chunked(3).map { it }.toSet()
		val h = have.chunked(3).map { it }.toSet()
		return w to h
	}

	override fun part1(lines: List<String>): Int = lines
		.map(::parseLine)
		.sumOf {
			val count = it.first.intersect(it.second).size
			if (count == 0) 0 else 1 shl (count - 1)
		}

	data class CardInfo(val win: Int, var count: Int = 1)

	override fun part2(lines: List<String>): Int = lines
		.map(::parseLine)
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
