package y2022.day06

import Day


fun main() {
	AOC
}

private object AOC : Day<Int, Int>(7, 19, 1531, 2518) {

	init {
		part1Text { it.windowedSequence(4).indexOfFirst { it.toSet().size == 4 } + 4 }
		part2Text { it.windowedSequence(14).indexOfFirst { it.toSet().size == 14 } + 14 }
	}
}
