package y2022.day08

import Day

data class P(val x: Int, val y: Int)

class Grid(val lines: List<String>) {
	val xRange = lines[0].indices
	val yRange = lines.indices

	operator fun get(x: Int, y: Int) = lines[y][x]
	operator fun get(p: P) = lines[p.y][p.x]

	private fun Iterable<Int>.countUntil(predicate: (Int) -> Boolean): Int {
		var count = 0
		for (element in this) {
			++count
			if (predicate(element))
				break
		}
		return count
	}


	fun findScore(p: P): Int {
		val treeHeight = get(p)

		val top = p.y - 1 downTo 0
		val bottom = p.y + 1..yRange.last
		val left = p.x - 1 downTo 0
		val right = p.x + 1..xRange.last

		val d1 = top.countUntil { get(p.x, it) >= treeHeight }
		val d2 = bottom.countUntil { get(p.x, it) >= treeHeight }
		val d3 = left.countUntil { get(it, p.y) >= treeHeight }
		val d4 = right.countUntil { get(it, p.y) >= treeHeight }

		return d1 * d2 * d3 * d4
	}

	private fun checkRanges(r: IntRange, cut: Int, block: (Int) -> Boolean): Boolean {
		val r1 = 0 until cut
		val r2 = cut + 1 until r.last + 1
		return (!r1.isEmpty() && r1.all(block)) || (!r2.isEmpty() && r2.all(block))
	}

	fun isVisible(p: P): Boolean {
		if (p.x == 0 || p.y == 0 || p.x == xRange.last || p.y == yRange.last) return true
		val treeHeight = get(p)
		return checkRanges(xRange, p.x) { get(it, p.y) < treeHeight } ||
				checkRanges(yRange, p.y) { get(p.x, it) < treeHeight }
	}
}

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(21, 8, 1851, 574080) {

	init {
		part1Lines { lines ->
			val g = Grid(lines)

			var visibleTrees = 0

			for (x in g.xRange)
				for (y in g.yRange) {
					val p = P(x, y)
					if (g.isVisible(p))
						visibleTrees++
				}

			visibleTrees
		}

		part2Lines { lines ->
			val g = Grid(lines)
			var max = -1

			for (x in g.xRange) {
				for (y in g.yRange) {
					val p = P(x, y)
					val d = g.findScore(p)
					max = max.coerceAtLeast(d)
				}
			}

			max
		}
	}

}