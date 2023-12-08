package day08

import DayList
import findLCM

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Long>(2, 6L, 21883, 12833235391111L) {

	var directions = ""
	var map = emptyMap<String, Pair<String, String>>()

	fun parse(lines: List<String>) {
		directions = lines[0]
		map = lines.drop(2).associate {
			it.substring(0, 3) to (it.substring(7, 10) to it.substring(12, 15))
		}
	}

	fun String.next(dir: Char): String {
		val pair = map[this]!!
		return when (dir) {
			'L'  -> pair.first
			else -> pair.second
		}
	}

	override fun part1(lines: List<String>): Int {
		parse(lines)
		var current = "AAA"
		var count = 0
		while (true) {
			directions.forEach { direction ->
				count++
				current = current.next(direction)
				if (current == "ZZZ")
					return count
			}
		}
	}

	class Ghost(val name: String) {
		var zLoopCount = 0L

		fun findZs() {
			var current = name
			var count = 0L
			while (true) {
				directions.forEach { dir ->
					count++
					current = current.next(dir)
					if (current.endsWith("Z")) {
						zLoopCount = count
						return
					}
				}
			}
		}
	}

	override fun part2(lines: List<String>): Long {
		parse(lines)
		val ghosts = map.keys.filter { it.endsWith("A") }
			.map { Ghost(it) }
			.onEach { it.findZs() }
		return findLCM(ghosts.map { it.zLoopCount })
	}
}