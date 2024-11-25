package y2020.day01

import y2020.readInputAsList
import y2020.readTestAsList


fun main() {
	fun part1(list: List<String>): Int {
		val set = list.map { it.toInt() }.toSet()
		val set2 = set.filter { 2020 - it in set }
		val (v1, v2) = set2
		return v1 * v2
	}

	fun part2(list: List<String>): Int {
		val intList = list.map { it.toInt() }
		intList.forEach { v0 ->
			val x = 2020 - v0
			val set2 = intList.filter { x - it in intList }
			if (set2.isNotEmpty()) {
				val (v1, v2) = set2
				return v0 * v1 * v2
			}
		}

		return 1
	}

	// test if implementation meets criteria from the description, like:
	val testInput = readTestAsList()
	val input = readInputAsList()

	check(part1(testInput) == 514579)
	println(part1(input))

	check(part2(testInput) == 241861950)
	println(part2(input))
}
