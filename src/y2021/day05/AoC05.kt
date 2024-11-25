package y2021.day05

import y2021.readInputAsSequence

data class Point(val x: Int, val y: Int)

data class Line(val p1: Point, val p2: Point) {

	fun asSequence(): Sequence<Point> = when {
		p1.x == p2.x -> {
			val x = p1.x
			if (p1.y > p2.y)
				(p1.y downTo p2.y).map { Point(x, it) }.asSequence()
			else
				(p1.y..p2.y).map { Point(x, it) }.asSequence()
		}
		p1.y == p2.y -> {
			val y = p1.y
			if (p1.x > p2.x)
				(p1.x downTo p2.x).map { Point(it, y) }.asSequence()
			else
				(p1.x..p2.x).map { Point(it, y) }.asSequence()
		}
		else         -> {
			val dx = if (p1.x < p2.x) 1 else -1
			val dy = if (p1.y < p2.y) 1 else -1
			val step = (if (p1.x < p2.x) p2.x - p1.x else p1.x - p2.x)
			(0..step).map { Point(p1.x + dx * it, p1.y + dy * it) }.asSequence()
		}
	}
}

fun main() {

	fun readAsSequences() = readInputAsSequence()
		.map {
			val (p1, p2) = it.split(" -> ")
			val (x1, y1) = p1.split(",").map { c -> c.toInt() }
			val (x2, y2) = p2.split(",").map { c -> c.toInt() }
			Line(Point(x1, y1), Point(x2, y2))
		}

	fun part1() {
		val map = hashMapOf<Point, Int>()

		readAsSequences()
			.filter {
				it.p1.x == it.p2.x || it.p1.y == it.p2.y
			}
			.forEach {
				it.asSequence().forEach { p ->
					map.merge(p, 1) { a, b -> a + b }
				}
			}

		val count = map.count { (_, count) -> count > 1 }

		println(count)
	}

	fun part2() {
		val map = hashMapOf<Point, Int>()

		readAsSequences()
			.forEach {
				it.asSequence().forEach { p ->
					map.merge(p, 1) { a, b -> a + b }
				}
			}

		val count = map.count { (_, count) -> count > 1 }

		println(count)
	}

	part1()
	part2()
}
