package y2022.day17

import aoc2022.day17.Wall
import download
import readLines

fun main() {

	download()

	fun part1(lines: List<String>): Int {
		val wall = Wall(7, lines)
		repeat(2022) {
			wall.dropNewRock()
		}
//		wall.debug()
		return wall.stack.size
	}

	fun part2(lines: List<String>): Long {
		val wall = Wall(7, lines)
		val hMap = HashMap<Int, Pair<Long, Int>>()
		var toAdd = 0L
		var toDo = 0L
		for (i in 1..1000000000000L) {
			wall.dropNewRock()
			if (i > 100) {
				val hash = wall.stack.takeLast(30).joinToString { String(it) }.hashCode()
				val last = hMap[hash]
				if (last != null) {
					val (lastI, lastSize) = last
					val deltaI = i - lastI
					val deltaRows = wall.stack.size - lastSize
					val n = (1000000000000L - i) / deltaI
					toAdd = deltaRows * n
					toDo = 1000000000000L - i - deltaI * n
					break
				} else {
					hMap[hash] = i to wall.stack.size
				}
			}
		}
		repeat(toDo.toInt()) {
			wall.dropNewRock()
		}

		return wall.stack.size + toAdd
	}


	readLines(3068, 1514285714288L, ::part1, ::part2)
}