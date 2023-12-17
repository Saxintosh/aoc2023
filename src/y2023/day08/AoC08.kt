package y2023.day08

import Day
import findLCM

fun main() {
	AOC
}

private object AOC : Day<Int, Long>(2, 6L, 21883, 12833235391111L) {

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

	init {
		part1Lines { lines ->
			parse(lines)

			var current = "AAA"
			var idx = 0
			for (dir in dirGenerator()) {
				current = current.next(dir)
				idx++
				if (current == "ZZZ")
					break
			}
			idx
		}


		part2Lines { lines ->
			parse(lines)
			val ghosts = map.keys.filter { it.endsWith("A") }
				.map { Ghost(it) }
			findLCM(ghosts.map { it.zLoopCount })
		}
	}
}