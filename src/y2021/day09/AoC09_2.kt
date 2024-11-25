package y2021.day09

import y2021.readInputAsList

fun main() {

	data class Point(val x: Int, val y: Int, val ch: Char) {
		var min: Point? = null
	}

	class Grid(list: List<String>) {

		val cols = list[0].length
		val rows = list.size

		val data = Array(rows) { row ->
			Array(cols) { col -> Point(col, row, list[row][col]) }
		}

		private fun Point.isMinimum(): Boolean {
			val up = data.getOrNull(y - 1)?.getOrNull(x)?.ch ?: '9'
			val down = data.getOrNull(y + 1)?.getOrNull(x)?.ch ?: '9'
			val left = data.getOrNull(y)?.getOrNull(x - 1)?.ch ?: '9'
			val right = data.getOrNull(y)?.getOrNull(x + 1)?.ch ?: '9'
			val me = ch

			return me < up && me < down && me < left && me < right
		}

		fun findLowPoints() {
			data.forEach { points ->
				points.forEach { point ->
					if (point.isMinimum())
						point.min = point
				}
			}
		}

		private fun Point.getMinimum(): Point {
			val candidates = listOfNotNull(
				this,
				data.getOrNull(y - 1)?.getOrNull(x),
				data.getOrNull(y + 1)?.getOrNull(x),
				data.getOrNull(y)?.getOrNull(x - 1),
				data.getOrNull(y)?.getOrNull(x + 1)
			).filter { it.ch <= ch }
			val localMinimum = candidates.minByOrNull { it.ch }!!
			val minimum = localMinimum.min ?: localMinimum.getMinimum()
			candidates.forEach { it.min = minimum }
			return minimum
		}


		fun findBasin() {
			data.forEach { points ->
				points.forEach { point ->
					if (point.ch != '9' && point.min == null) {
						point.getMinimum()
					}
				}
			}
		}

	}

	val grid = Grid(readInputAsList())
	grid.findLowPoints()
	val part1 = grid.data.asSequence().flatMap { it.asSequence() }.filter { it.min != null }.sumOf { it.ch.digitToInt() + 1 }

	println(part1)

	grid.findBasin()
	val part2 = grid.data.asList().flatMap { it.asSequence() }
		.groupBy { it.min }
		.filterKeys { it != null }
		.mapValues { (_, list) -> list.size }
		.toList()
		.sortedBy { it.second }
		.takeLast(3)
		.map { it.second }
	val (a, b, c) = part2
	println(a * b * c)
}
