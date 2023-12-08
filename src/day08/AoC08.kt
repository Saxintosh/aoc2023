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
		map = lines.asSequence().drop(2).associate {
			it.substring(0, 3) to (it.substring(7, 10) to it.substring(12, 15))
		}
	}

	fun dirGenerator() = sequence {
		var currentIndex = 0
		val len = directions.length
		while (true) {
			yield(directions[currentIndex])
			currentIndex = (currentIndex + 1) % len
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
		dirGenerator().foldIndexed("AAA") { idx, current, dir ->
			current.next(dir).also {
				if (it == "ZZZ")
					return idx + 1
			}
		}
		return -1
	}

	class Ghost(val name: String) {
		var zLoopCount = 0L

		fun findZs() {
			dirGenerator().fold(name) { current, dir ->
				zLoopCount++
				current.next(dir).also {
					if (it.endsWith("Z"))
						return
				}
			}
		}

		init {
			findZs()
		}
	}


	override fun part2(lines: List<String>): Long {
		parse(lines)
		val ghosts = map.keys.filter { it.endsWith("A") }
			.map { Ghost(it) }
		return findLCM(ghosts.map { it.zLoopCount })
	}
}