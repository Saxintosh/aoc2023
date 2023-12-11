package day11

import ChGrid
import DayList
import Point
import myRange

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(374L, 82000210L, 10228230L, 447073334102L) {
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

	private fun pairs(lines: List<String>): List<Pair<Point, Point>> {
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

	override fun part1(lines: List<String>): Long {
		expansion = 2
		return pairs(lines).sumOf { shortestPaths(it) }
	}

	override fun part2(lines: List<String>): Long {
		expansion = 1_000_000
		return pairs(lines).sumOf { shortestPaths(it) }
	}
}