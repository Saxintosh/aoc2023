package y2015.day02

import Day


fun main() {
	AOC2
}

private object AOC2: Day<Int, Int>(101, 48, 1588178, 3783758) {

	init {
		part1Lines { lines ->
			var tot = 0
			lines.forEach {
				val (a, b, c) = it.split("x").map { it.toInt() }
				val s1 = a * b
				val s2 = b * c
				val s3 = a * c
				val min = minOf(s1, s2, s3)
				tot += (2 * s1 + 2 * s2 + 2 * s3 + min)
			}
			tot
		}

		part2Lines { lines ->
			var tot = 0
			lines.forEach {
				val (a, b, c) = it.split("x").map { it.toInt() }
				val l = a + b + c - maxOf(a, b, c)
				tot += 2 * l
				tot += (a * b * c)
			}
			tot
		}
	}
}