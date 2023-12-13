package day13

import DayList
import kotlin.math.min


fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(405, 400, 30535, 30844) {

	var strictCheck = true

	fun parse(lines: List<String>): List<List<String>> = lines.fold(mutableListOf(mutableListOf<String>())) { acc, line ->
		when (line) {
			""   -> acc.add(mutableListOf())
			else -> acc.last().add(line)
		}
		acc
	}

	tailrec fun findVerticalMirror(left: List<String>, right: List<String>): Int {
		if (right.isEmpty())
			return 0

		val min = min(left.size, right.size)
		val a = left.take(min)
		val b = right.take(min)
		val equals = if (strictCheck) a == b else equalsIsh(a, b)
		if (equals)
			return left.size

		return findVerticalMirror(right.take(1) + left, right.drop(1))
	}

	// In this case, the two blocks are almost equal if the difference is only in one character.
	// For each pair of rows, I count the differences.
	// The total sum must be 1
	fun equalsIsh(a: List<String>, b: List<String>) = a.zip(b)
		.sumOf {
			it.first.zip(it.second).count { it.first != it.second }
		}.let { it == 1 }

	fun List<String>.rotate() = this[0].indices.map { index ->
		buildString {
			this@rotate.forEach { append(it[index]) }
		}
	}

	fun findMirrorValue(lines: List<String>): Int {
		val value = findVerticalMirror(lines.take(1), lines.drop(1))
		if (value > 0)
			return value * 100

		val rotatedLines = lines.rotate()
		return findVerticalMirror(rotatedLines.take(1), rotatedLines.drop(1))
	}

	override fun part1(lines: List<String>) = parse(lines).sumOf { findMirrorValue(it) }
	override fun part2(lines: List<String>) = parse(lines).also { strictCheck = false }.sumOf { findMirrorValue(it) }
}
