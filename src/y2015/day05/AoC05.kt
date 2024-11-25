package y2015.day05

import Day


fun main() {
	AOC5
}


private object AOC5: Day<Int, Int>(2, 2, 238, 69) {

	val couples = setOf("ab", "cd", "pq", "xy")

	fun String.threeVowels() = count { it in "aeiou" } >= 3
	fun String.atLeastOneDuplication() = windowed(2).any { it[0] == it[1] }
	fun String.noCustomCouples() = windowed(2).none { it in couples }

	fun String.pairOfTwo(): Boolean {
		for (i in 2 until length - 1) {
			val left = substring(i - 2, i)
			val right = substring(i)
			if (left in right) {
				return true
			}
		}
		return false
	}

	fun String.aXa() = windowed(3).any { it[0] == it[2] }

	init {
		part1Lines { lines ->
			lines.count {
				it.threeVowels() && it.atLeastOneDuplication() && it.noCustomCouples()
			}
		}

		part2Lines { lines ->
			lines.count {
				it.pairOfTwo() && it.aXa()
			}
		}
	}
}