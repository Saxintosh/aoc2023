package y2022.day15

import download
import readLines
import kotlin.math.abs
import kotlin.math.max

private data class P(val x: Int, val y: Int) {
	val tuningFrequency get() = x * 4000000L + y
}

private data class B(val p: P, val b: P) {
	val radius = p distance b
	override fun toString() = "(${p.x},${p.y})"

	fun rAtY(y: Int): IntRange? {
		val dx = radius - abs(p.y - y)
		return if (dx < 0)
			null
		else
			p.x - dx..p.x + dx
	}
}

private infix fun P.distance(p2: P) = abs(p2.x - x) + abs(p2.y - y)
private operator fun B.contains(p2: P) = p2 distance p <= radius

private fun parse(list: List<String>) = list.map {
	val (a, b) = it.split(": ")
	val (a1, a2) = a.split(", ")
	val px = a1.removePrefix("Sensor at x=").toInt()
	val py = a2.removePrefix("y=").toInt()
	val (b1, b2) = b.split(", ")
	val bx = b1.removePrefix("closest beacon is at x=").toInt()
	val by = b2.removePrefix("y=").toInt()
	B(P(px, py), P(bx, by))
}

fun main() {

	download()

	val yy = mutableListOf(10, 2000000)

	fun part1(lines: List<String>): Int {
		val beacons = parse(lines)
		val y = yy.removeFirst()
		var pLeft = P(0, y)
		while (true) {
			if (beacons.any { pLeft in it })
				pLeft = P(pLeft.x - 1, y)
			else
				break
		}
		var pRight = P(0, y)
		while (true) {
			if (beacons.any { pRight in it })
				pRight = P(pRight.x + 1, y)
			else
				break
		}
		return abs(pLeft.x) - 1 + abs(pRight.x) - 1
	}

	val maxs = mutableListOf(20, 4000000)

	fun part2(lines: List<String>): Long {
		val beacons = parse(lines)
		val max = maxs.removeFirst()

		for (y in 0..max) {
			var lastX = -1
			beacons.mapNotNull { it.rAtY(y) }.sortedBy { it.first }.forEach {
				val x = lastX + 1
				if (it.first > x)
					return P(x,y).tuningFrequency
				else
					lastX = max(lastX, it.last)
			}
		}

		return 0
	}


	readLines(26, 56000011L, ::part1, ::part2)
}