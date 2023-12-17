package y2023.day10

import ChGrid
import Day
import Point

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(8, 10, 7086, 317) {

	fun ChGrid.next(pPre: Point, pCur: Point): Point {
		return when (get(pCur)!!) {
			'|'  -> (listOf(pCur.up(), pCur.down()) - pPre).first()
			'-'  -> (listOf(pCur.left(), pCur.right()) - pPre).first()
			'L'  -> (listOf(pCur.up(), pCur.right()) - pPre).first()
			'J'  -> (listOf(pCur.up(), pCur.left()) - pPre).first()
			'7'  -> (listOf(pCur.left(), pCur.down()) - pPre).first()
			'F'  -> (listOf(pCur.down(), pCur.right()) - pPre).first()
			'.'  -> TODO()
			'S'  -> TODO()
			else -> TODO()
		}
	}

	fun findLoop(pre: Point, cur: Point, grid: ChGrid): List<Point> {
		var pre1 = pre
		var cur1 = cur
		val loop = buildList {
			add(pre1)
			add(cur1)
			while (true) {
				val next = grid.next(pre1, cur1)
				pre1 = cur1
				cur1 = next
				if (grid[cur1] == 'S')
					break
				add(cur1)
			}
		}
		return loop
	}

	fun firstNext(s: Point, grid: ChGrid): Point = buildList {
		s.up().let { if (grid.isInRange(it) && grid[it]!! in "7F") add(it) }
		s.down().let { if (grid.isInRange(it) && grid[it]!! in "|LJ") add(it) }
		s.left().let { if (grid.isInRange(it) && grid[it]!! in "-FL") add(it) }
		s.right().let { if (grid.isInRange(it) && grid[it]!! in "-J7") add(it) }
	}
		.first()

	fun ChGrid.castRay(start: Point, loop: List<Point>): Int {
		var tiles = 0
		var inside = false
		var p = start
		while (isInRange(p)) {
			val ch = get(p)
			if (p in loop) {
				if (ch != '7' && ch != 'L') // a corner doesn't change the status
					inside = !inside
			} else {
				if (inside) {
					tiles++
				}
			}
			p = p.downRight()
		}
		return tiles
	}

	init {
		part1Lines { lines ->
			val grid = ChGrid(lines)
			val s = grid.asPointsSequence().first { grid[it] == 'S' }
			val next = firstNext(s, grid)

			val loop = findLoop(s, next, grid)
			loop.size / 2
		}


		part2Lines { lines ->
			val grid = ChGrid(lines)
			val s = grid.asPointsSequence().first { grid[it] == 'S' }
			val next = firstNext(s, grid)

			val loop = findLoop(s, next, grid)

			// https://en.wikipedia.org/wiki/Point_in_polygon
			// Throwing a 45-degree beam to simplify cases of state change.
			// Specifically, when a ray encounters an angle, 7 or L, the inside/outside state does not change.

			val startList = grid.xRange.map { Point(it, 0) } + grid.yRange.map { Point(0, it) } - Point(0, 0)

			startList.sumOf { grid.castRay(it, loop) }
		}
	}
}