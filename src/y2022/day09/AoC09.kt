package y2022.day09

import Day
import kotlin.math.abs

data class P(val x: Int, val y: Int) {
	fun up() = P(x + 1, y)
	fun down() = P(x - 1, y)
	fun left() = P(x, y - 1)
	fun right() = P(x, y + 1)
}

class Board(private val len: Int) {
	private var rope = Array(len) { P(0, 0) }
	private var head
		get() = rope[0]
		set(p: P) {
			rope[0] = p
		}
	private val tail get() = rope.last()
	val tailSet = HashSet<P>().apply { add(tail) }

	private fun moveTails() {
		for (k in 1 until len) {
			val dx = rope[k - 1].x - rope[k].x
			val dy = rope[k - 1].y - rope[k].y

			if (abs(dx) <= 1 && abs(dy) <= 1)
				continue

			if (abs(dx) == 2 && abs(dy) == 2) {
				rope[k] = P(rope[k].x + dx / 2, rope[k].y + dy / 2)
				continue
			}

			// solo uno è a 2
			when {
				dx == 2  -> rope[k] = rope[k - 1].down()
				dx == -2 -> rope[k] = rope[k - 1].up()
				dy == 2  -> rope[k] = rope[k - 1].left()
				dy == -2 -> rope[k] = rope[k - 1].right()
			}
		}
		tailSet.add(tail)
	}

	fun process(line: String, draw: Boolean = false) {
		val (p1, p2) = line.split(" ")
		val rep = p2.toInt()
		when (p1) {
			"U" -> repeat(rep) { head = head.up(); moveTails() }
			"D" -> repeat(rep) { head = head.down(); moveTails() }
			"L" -> repeat(rep) { head = head.left(); moveTails() }
			"R" -> repeat(rep) { head = head.right(); moveTails() }
		}

		if (draw)
			draw()
	}

	fun draw() {
		val fullSet = tailSet.plus(rope)
		val xr = fullSet.minOf { it.x }..fullSet.maxOf { it.x }
		val yr = fullSet.maxOf { it.y } downTo fullSet.minOf { it.y }

		for (y in yr) {
			for (x in xr) {
				val curr = P(x, y)
				val ch = when {
					curr == head       -> "H"
					curr in rope       -> rope.indexOfFirst { it == curr }.toString()
					0 == x && 0 == y   -> "s"
					P(x, y) in tailSet -> "#"
					else               -> "."
				}
				print(ch)
			}
			println()
		}

		println()
	}
}

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(88, 36, 5930, 2443) {

	init {
		part1Lines { lines ->
			val b = Board(2)
			lines.forEach(b::process)
			b.tailSet.count()
		}

		part2Lines { lines ->
			val b = Board(10)
			lines.forEach(b::process)
			b.tailSet.count()
		}
	}
}