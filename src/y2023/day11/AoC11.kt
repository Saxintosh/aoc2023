package y2023.day11

import ChGrid
import Day
import Point
import myRange

fun main() {
	AOC
}

private object AOC : Day<Long, Long>(374L, 82000210L, 10228230L, 447073334102L) {

	var xExp = emptyList<Int>()
	var yExp = emptyList<Int>()

	var expansion = 0L

	fun shortestPaths(pair: Pair<Point, Point>): Long {
		val (p1, p2) = pair
		val rx = myRange(p1.x, p2.x)
		val ry = myRange(p1.y, p2.y)

		var res = (rx.last() - rx.first()).toLong()
		res += (ry.last() - ry.first()).toLong()
		res += (rx.count { it in xExp }).toLong() * (expansion - 1)
		res += (ry.count { it in yExp }).toLong() * (expansion - 1)

		return res
	}

	fun pairs(lines: List<String>): List<Pair<Point, Point>> {
		val grid = ChGrid(lines)
		yExp = grid.yRange.filter { y -> lines[y].all { it == '.' } }
		xExp = grid.xRange.filter { x ->
			grid.yRange.all { y -> grid[x, y] == '.' }
		}

		val galaxies = grid.asPointsSequence().filter { grid[it] == '#' }.toList()
		val pairs = galaxies.let { list ->
			buildList {
				val size = list.size
				for (a in 0 until size)
					for (b in (a + 1) until size) {
						add(list[a] to list[b])
					}
			}
		}
		return pairs
	}

	init {
		part1Lines { lines ->
			expansion = 2
			pairs(lines).sumOf { shortestPaths(it) }
		}

		part2Lines { lines ->
			expansion = 1_000_000
			pairs(lines).sumOf { shortestPaths(it) }
		}
	}
}