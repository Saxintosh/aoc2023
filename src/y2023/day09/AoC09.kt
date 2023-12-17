package y2023.day09

import Day

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(114, 2, 1898776583, 1100) {

	tailrec fun List<Int>.findNext(nextAcc: Int = 0): Int {
		val next = nextAcc + last()
		val l = zipWithNext { a, b -> b - a }
		return if (l.all { it == 0 }) next else l.findNext(next)
	}

	init {
		part1Lines { lines ->
			lines.asSequence()
				.map { line -> line.split(" ").map { it.toInt() } }
				.sumOf {
					it.findNext()
				}
		}

		part2Lines { lines ->
			lines.asSequence()
				.map { line -> line.split(" ").map { it.toInt() } }
				.map { it.reversed() }
				.sumOf {
					it.findNext()
				}
		}
	}
}