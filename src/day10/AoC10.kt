package day10

import ChGrid
import DayList
import Point

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(8, 1) {
	fun ChGrid.next(pPre: Point, pCur: Point): Point {
		val ch = get(pCur)!!
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

	override fun part1(lines: List<String>): Int {
		val grid = ChGrid(lines)
		val s = grid.asPointsSequence().first { grid[it] == 'S' }
		var  cur= buildList {
			s.up().let { if (grid.isInRange(it) && grid[it]!! in "7F") add(it) }
			s.down().let { if (grid.isInRange(it) && grid[it]!! in "|LJ") add(it) }
			s.left().let { if (grid.isInRange(it) && grid[it]!! in "-FL") add(it) }
			s.right().let { if (grid.isInRange(it) && grid[it]!! in "-J7") add(it) }
		}.first()
		var pre = s
		var count = 1
		while (true) {
			count ++
			val next = grid.next(pre, cur)
			pre = cur
			cur = next
			if (grid[cur] == 'S')
				break
		}

		return count / 2
	}

	override fun part2(lines: List<String>) = 1
}