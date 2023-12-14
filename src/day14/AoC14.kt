package day14

import ChGrid
import DayList
import Point

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(136, 64, 108641, 84328) {

	tailrec fun ChGrid.roll(p: Point, dir: (Point) -> Point) {
		val p2 = dir(p)
		if (isInRange(p2) && this[p2] == '.') {
			set(p, '.')
			set(p2, 'O')
			roll(p2, dir)
		}
	}

	fun ChGrid.tiltNorth() {
		yRange.forEach { y ->
			xRange.forEach { x ->
				val p = Point(x, y)
				if (this[p] == 'O')
					roll(p, Point::up)
			}
		}
	}

	fun ChGrid.tiltSouth() {
		yRange.reversed().forEach { y ->
			xRange.forEach { x ->
				val p = Point(x, y)
				if (this[p] == 'O')
					roll(p, Point::down)
			}
		}
	}

	fun ChGrid.tiltWest() {
		xRange.forEach { x ->
			yRange.forEach { y ->
				val p = Point(x, y)
				if (this[p] == 'O')
					roll(p, Point::left)
			}
		}
	}

	fun ChGrid.tiltEst() {
		xRange.reversed().forEach { x ->
			yRange.forEach { y ->
				val p = Point(x, y)
				if (this[p] == 'O')
					roll(p, Point::right)
			}
		}
	}

	fun ChGrid.tiltCycle() {
		tiltNorth()
		tiltWest()
		tiltSouth()
		tiltEst()
	}

	fun ChGrid.tiltCycle(iteration: Int) {
		val cache = HashMap<Int, Int>()
		var cycles = 0
		var hash = 0
		while (cycles < iteration) {
			cycles++
			tiltCycle()
			hash = lines.joinToString { it.joinToString("") }.hashCode()
			if (hash in cache)
				break
			cache[hash] = cycles
		}
		val exIteration = cache[hash]!!
		val cycleLen = cycles - exIteration
		val rem = (iteration-cycles)%cycleLen
		repeat(rem) {
			tiltCycle()
		}
	}

	fun ChGrid.northLoad(): Int {
		val rows = yRange.last + 1
		return yRange.sumOf { y ->
			lines[y].count { it == 'O' } * (rows - y)
		}
	}

	override fun part1(lines: List<String>): Int {
		val grid = ChGrid(lines)
		grid.tiltNorth()
		return grid.northLoad()
	}

	override fun part2(lines: List<String>): Int {
		val grid = ChGrid(lines)
		grid.tiltCycle(1000000000)
		return grid.northLoad()
	}
}
