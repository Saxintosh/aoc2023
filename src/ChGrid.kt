data class Point(val x: Int, val y: Int) {
	fun up() = Point(x, y - 1)
	fun down() = Point(x, y + 1)
	fun left() = Point(x - 1, y)
	fun upLeft() = Point(x - 1, y - 1)
	fun downLeft() = Point(x - 1, y + 1)
	fun right() = Point(x + 1, y)
	fun upRight() = Point(x + 1, y - 1)
	fun downRight() = Point(x + 1, y + 1)

	fun adjacent() = buildList {
		add(up())
		add(down())
		add(left())
		add(right())
		add(upLeft())
		add(upRight())
		add(downLeft())
		add(downRight())
	}
}

data class HRange(val y: Int, val range: IntRange)

class ChGrid(val lines: List<String>) {
	val xRange = lines[0].indices
	val yRange = lines.indices

	fun getLine(y: Int) = lines.getOrNull(y)
	fun String.getCh(x: Int) = getOrNull(x)

	operator fun get(x: Int, y: Int) = getLine(y)?.getCh(x)
	operator fun get(p: Point) = get(p.x, p.y)
	operator fun get(p: Pair<Int, Int>) = get(p.first, p.second)

	fun extract(r: HRange) = lines[r.y].substring(r.range)

	fun asPointsSequence() = sequence {
		yRange.forEach { y ->
			xRange.forEach { x ->
				yield(Point(x, y))
			}
		}
	}

	fun isInRange(p: Point) = p.x in xRange && p.y in yRange
}
