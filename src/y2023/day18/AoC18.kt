package y2023.day18

import Day
import LPoint
import kotlin.math.abs

fun main() {
	AOC
}

private object AOC : Day<Long, Long>(62, 952408144115, 56923) {

	val points = mutableListOf<LPoint>()

	@Suppress("FunctionName")
	fun P(i: Int) = when (i) {
		-1          -> points.last()
		points.size -> points.first()
		else        -> points[i]
	}

	// The coordinates are relative to the centre of the single 1m square
	// So I use this incomplete area to calculate the internal area and then add the edge
	private fun areaWith(boundary: Long): Long {
		// https://en.wikipedia.org/wiki/Shoelace_formula
		// area with half border

		var a = 0L
		points.indices.forEach { i ->
			a += (P(i).x * (P(i + 1).y - P(i - 1).y))
		}
		a = abs(a) / 2

		// https://en.wikipedia.org/wiki/Pick%27s_theorem
		// internal area

		val i = a - (boundary / 2) + 1
		return i + boundary
	}

	init {
		part1Lines { lines ->
			points.clear()
			points.add(LPoint(0, 0))
			var boundary = 0L

			lines.forEach {
				val (dir, s) = it.split(" ")
				val steps = s.toInt()
				val delta = when (dir) {
					"U"  -> 0 to -1
					"D"  -> 0 to 1
					"L"  -> -1 to 0
					"R"  -> 1 to 0
					else -> TODO()
				}
				val p = points.last()
				val dx = delta.first * steps
				val dy = delta.second * steps
				val np = LPoint(p.x + dx, p.y + dy)
				points.add(np)
				boundary += steps
			}

			points.removeLast()

			areaWith(boundary)
		}

		part2Lines { lines ->
			points.clear()
			points.add(LPoint(0, 0))
			var boundary = 0L

			lines.forEach {
				val (_, _, code) = it.split(" ")
				val hex = code.drop(2).dropLast(1)
				val steps = hex.dropLast(1).toLong(16)
				val dir = hex.last()
				val delta = when (dir) {
					'3'  -> 0 to -1
					'1'  -> 0 to 1
					'2'  -> -1 to 0
					'0'  -> 1 to 0
					else -> TODO()
				}
				val p = points.last()
				val dx = delta.first * steps
				val dy = delta.second * steps
				val np = LPoint(p.x + dx, p.y + dy)
				points.add(np)
				boundary += steps
			}

			points.removeLast()

			areaWith(boundary)
		}
	}
}