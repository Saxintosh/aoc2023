package day13

import DayList


fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(405, 1, 30535) {

	fun parse(lines: List<String>): List<List<String>> = lines.fold(mutableListOf(mutableListOf<String>())) { acc, line ->
		when (line) {
			""   -> acc.add(mutableListOf())
			else -> acc.last().add(line)
		}
		acc
	}

	tailrec fun findVerticalMirror(left: List<String>, right: List<String>): List<String> {
		if (right.isEmpty())
			return emptyList()

		if (left.size <= right.size && left == right.take(left.size))
			return left

		if (left.size > right.size && left.take(right.size) == right)
			return left

		return findVerticalMirror(right.take(1) + left, right.drop(1))
	}

	fun List<String>.rotate() = this[0].indices.map { index ->
		buildString {
			this@rotate.forEach { append(it[index]) }
		}
	}

	fun findVerticalMirror(lines: List<String>): Int {
		var list = findVerticalMirror(lines.take(1), lines.drop(1))
		if (list.isNotEmpty())
			return (list.size) * 100

		val rotatedLines = lines.rotate()

		list = findVerticalMirror(rotatedLines.take(1), rotatedLines.drop(1))
		if (list.isNotEmpty())
			return list.size

		return 0
	}

	override fun part1(lines: List<String>) = parse(lines).sumOf { findVerticalMirror(it) }

	override fun part2(lines: List<String>) = 1
}
