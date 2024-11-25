package y2015.day06

import Day
import kotlin.math.max


fun main() {
	AOC6
}


private object AOC6: Day<Int, Int>(4, 4, 400410, 69) {

	fun Array<Int>.turnOff(x1: Int, x2: Int) = (x1 .. x2).forEach { this[it] = 0 }
	fun Array<Int>.turnOn(x1: Int, x2: Int) = (x1 .. x2).forEach { this[it] = 1 }
	fun Array<Int>.toggle(x1: Int, x2: Int) = (x1 .. x2).forEach { this[it] = if (this[it] == 1) 0 else 1 }

	fun Array<Int>.turnOff2(x1: Int, x2: Int) = (x1 .. x2).forEach { this[it] = max(this[it] - 1, 0) }
	fun Array<Int>.turnOn2(x1: Int, x2: Int) = (x1 .. x2).forEach { this[it] = this[it] + 1 }
	fun Array<Int>.toggle2(x1: Int, x2: Int) = (x1 .. x2).forEach { this[it] = this[it] + 2 }

	init {

		part1Lines { lines ->
			val grid = Array(1000) { Array(1000) { 0 } }
			lines.forEach { line ->
				var parts = line.split(" ", ",")
				if (parts[0] == "turn")
					parts = parts.drop(1)
				val cmd = parts[0]
				val x1 = parts[1].toInt()
				val y1 = parts[2].toInt()
				val x2 = parts[4].toInt()
				val y2 = parts[5].toInt()
				(y1 .. y2).forEach { y ->
					when (cmd) {
						"on"     -> grid[y].turnOn(x1, x2)
						"off"    -> grid[y].turnOff(x1, x2)
						"toggle" -> grid[y].toggle(x1, x2)
					}
				}
			}
			grid.sumOf { it.sum() }
		}

		part2Lines { lines ->
			val grid = Array(1000) { Array(1000) { 0 } }
			lines.forEach { line ->
				var parts = line.split(" ", ",")
				if (parts[0] == "turn")
					parts = parts.drop(1)
				val cmd = parts[0]
				val x1 = parts[1].toInt()
				val y1 = parts[2].toInt()
				val x2 = parts[4].toInt()
				val y2 = parts[5].toInt()
				(y1 .. y2).forEach { y ->
					when (cmd) {
						"on"     -> grid[y].turnOn2(x1, x2)
						"off"    -> grid[y].turnOff2(x1, x2)
						"toggle" -> grid[y].toggle2(x1, x2)
					}
				}
			}
			grid.sumOf { it.sum() }
		}
	}
}