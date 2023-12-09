package day09

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(114, 2, 1898776583) {

	// To find the preceding, because of the recursive operation (x - (...)),
	// to remove the parenthesis, I have to invert the content it follows so the sign alternates the + with the -
	tailrec fun List<Int>.findNext(prevAcc: Int = 0, sign: Int = -1, nextAcc: Int = 0): Pair<Int, Int> {
		val next = nextAcc + last()
		val prev = prevAcc - first() * sign
		val l = this.windowed(2, 1, false) { (a, b) -> b - a }
		return if (l.all { it == 0 }) (prev to next) else l.findNext(prev, sign * -1, next)
	}

	override fun part1(lines: List<String>) = lines.asSequence()
		.map { line -> line.split(" ").map { it.toInt() } }
		.sumOf {
			it.findNext().second
		}

	override fun part2(lines: List<String>) = lines.asSequence()
		.map { line -> line.split(" ").map { it.toInt() } }
		.sumOf {
			val p = it.findNext()
			p.first
		}
}

