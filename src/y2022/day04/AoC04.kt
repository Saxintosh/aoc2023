package y2022.day04

import Day


fun main() {
	AOC
}

private object AOC : Day<Int, Int>(2, 4, 569, 936) {

	fun String.toIntRange() = split("-").map { it.toInt() }.let { it[0]..it[1] }
	fun decode(line: String) = line.split(",").let { it[0].toIntRange() to it[1].toIntRange() }

	operator fun IntRange.contains(other: IntRange) = first <= other.first && last >= other.last
	infix fun IntRange.overlaps(other: IntRange) = last in other

	init {
		part1Lines { list ->
			list.count {
				val (r1, r2) = decode(it)
				r1 in r2 || r2 in r1
			}
		}

		part2Lines { list ->
			list.count {
				val (r1, r2) = decode(it)
				r1 overlaps r2 || r2 overlaps r1
			}
		}
	}
}
