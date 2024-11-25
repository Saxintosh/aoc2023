package y2022.day14

import download
import myRange
import readLines

private data class P(val x: Int, val y: Int) {
	fun up() = P(x, y - 1)
	fun down() = P(x, y + 1)
	fun left() = P(x - 1, y)
	fun right() = P(x + 1, y)
	override fun toString() = "($x,$y)"
}

private class Regolith {
	private val map = HashMap<P, Char>()
	private var rocks = 0
	private var deepestRock = 0
	private var floor = 0
	private var fallingForeverDetected = false
	private var isFull = false

	fun parse(lines: List<String>) {
		lines.forEach {
			it.split(" -> ").windowed(2) { (a, b) ->
				val s1 = a.split(",").map(String::toInt)
				val s2 = b.split(",").map(String::toInt)
				val p1 = P(s1[0], s1[1])
				val p2 = P(s2[0], s2[1])
				when (p1.x == p2.x) {
					true  -> for (y in myRange(p1.y, p2.y)) {
						map[P(p1.x, y)] = '#'
					}

					false -> for (x in myRange(p1.x, p2.x)) {
						map[P(x, p1.y)] = '#'
					}
				}
			}
		}

		deepestRock = map.keys.maxBy { it.y }.y
		floor = deepestRock + 1
		rocks = map.size
	}

	val size get() = map.size - rocks

	fun P.next(): P? {
		val p1 = down()
		if (map[p1] == null) return p1
		val p2 = p1.left()
		if (map[p2] == null) return p2
		val p3 = p1.right()
		if (map[p3] == null) return p3
		return null
	}

	fun dropSand() {
		var p = P(500, 0)
		var next = p.next()
		while (next != null && next.y < deepestRock) {
			p = next
			next = p.next()
		}

		if (next == null)
			map[p] = 'o'
		else
			fallingForeverDetected = true
	}

	fun dropSand2() {
		var p = P(500, 0)
		var next = p.next()
		while (next != null && next.y < floor) {
			p = next
			next = p.next()
		}

		if (next == null) {
			map[p] = 'o'
			if (p.y == 0)
				isFull = true
		} else {
			map[next] = 'o'
		}
	}

	fun process() {
		while (!fallingForeverDetected)
			dropSand()
	}
	fun process2() {
		while (!isFull)
			dropSand2()
	}
}


fun main() {

	download()

	fun part1(lines: List<String>): Int {
		val r = Regolith()
		r.parse(lines)
		r.process()
		return r.size
	}

	fun part2(lines: List<String>): Int {
		val r = Regolith()
		r.parse(lines)
		r.process2()
		return r.size
	}


	readLines(24, 93, ::part1, ::part2)
}