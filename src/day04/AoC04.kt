package day04

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(13, 30) {

	override fun part1(lines: List<String>): Int = lines.asSequence()
		.map { it.substringAfter(": ") }
		.map {
			val (win, have) = it.split(" | ")
			val w = win.chunked(3).map { it.trim().toInt() }.toSet()
			val h = have.chunked(3).map { it.trim().toInt() }.toSet()
			w to h
		}
		.map {
			it.first.intersect(it.second).size
		}
		.map {
			when (it) {
				0    -> 0
				else -> 1 shl (it - 1)
			}
		}
		.sum()

	data class CardInfo(val win: Int, var count: Int = 1)

	override fun part2(lines: List<String>): Int {
		val list = lines
			.map { it.substringAfter(": ") }
			.map {
				val (win, have) = it.split(" | ")
				val w = win.chunked(3).map { it.trim().toInt() }.toSet()
				val h = have.chunked(3).map { it.trim().toInt() }.toSet()
				w to h
			}
			.map {
				CardInfo(it.first.intersect(it.second).size)
			}

		list.forEachIndexed { index, card ->
			(index + 1 .. index + card.win).forEach {
				list[it].count += card.count
			}
		}

		return list.sumOf { it.count }
	}
}