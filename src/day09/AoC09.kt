package day09

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(114, 2, 1898776583) {

	tailrec fun List<Int>.findNext(nextAcc: Int = 0): Int {
		val next = nextAcc + last()
		val l = zipWithNext { a, b -> b - a }
		return if (l.all { it == 0 }) next else l.findNext(next)
	}

	override fun part1(lines: List<String>) = lines.asSequence()
		.map { line -> line.split(" ").map { it.toInt() } }
		.sumOf {
			it.findNext()
		}

	override fun part2(lines: List<String>)  = lines.asSequence()
		.map { line -> line.split(" ").map { it.toInt() } }
		.map { it.reversed() }
		.sumOf {
			it.findNext()
		}
}