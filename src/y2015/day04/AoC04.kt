package y2015.day04

import Day
import md5


fun main() {
	AOC4
}

private data class P(val x: Int, val y: Int) {
	fun up() = P(x, y + 1)
	fun down() = P(x, y - 1)
	fun left() = P(x - 1, y)
	fun right() = P(x + 1, y)
}

private object AOC4: Day<Int, Int>(609043, 6742839, 282749, 9962624) {

	init {
		part1Text { txt ->
			generateSequence(1) { it + 1 }
				.first { (txt + it.toString()).md5().startsWith("00000") }
		}

		part2Text { txt ->
			generateSequence(1) { it + 1 }
				.first { (txt + it.toString()).md5().startsWith("000000") }
		}
	}
}