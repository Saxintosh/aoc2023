package day11

import ChGrid
import DayList
import Point
import myRange

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Long>(374, 82000210L) {
	var xExp = emptyList<Int>()
	var yExp = emptyList<Int>()

	fun shortestPaths(pair: Pair<Point, Point>): Int {
		val (p1, p2) = pair
		val rx = myRange(p1.x, p2.x)
		val ry = myRange(p1.y, p2.y)

		var res = rx.last() - rx.first()
		res += (ry.last() - ry.first())
		res += (rx.count { it in xExp })
		res += (ry.count { it in yExp })

		return res
	}

	fun shortestPaths2(pair: Pair<Point, Point>): Long {
		val (p1, p2) = pair
		val rx = myRange(p1.x, p2.x)
		val ry = myRange(p1.y, p2.y)

		var res = rx.last() - rx.first()
		res += (ry.last() - ry.first())

		var res2 = res.toLong()
		res2 += (rx.count { it in xExp }).toLong() * (1000000L - 1)
		res2 += (ry.count { it in yExp }).toLong() * (1000000L - 1)

		return res2
	}

	override fun part1(lines: List<String>): Int {
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

		return pairs.sumOf { shortestPaths(it) }
	}

	override fun part2(lines: List<String>): Long {
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

		return pairs.sumOf { shortestPaths2(it) }
	}
}