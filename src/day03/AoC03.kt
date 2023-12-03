package day03

import ChGrid
import DayList
import HRange
import Point

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Long>(4361, 467835L) {

	val regex = "(\\d+)".toRegex()

	fun ChGrid.findRangeNumbers(y: Int) = regex.findAll(lines[y]).map { HRange(y, it.range) }.toList()

	fun getPerimeter(hr: HRange) = buildList {
		hr.range.forEach {
			val p = Point(it, hr.y)
			add(p.up())
			add(p.down())
		}
		val first = Point(hr.range.first, hr.y)
		add(first.left())
		add(first.upLeft())
		add(first.downLeft())
		val last = Point(hr.range.last, hr.y)
		add(last.right())
		add(last.upRight())
		add(last.downRight())
	}

	fun ChGrid.isSymbol(p: Point): Boolean {
		val ch = get(p)
		return ch != null && !(ch.isDigit() || ch == '.')
	}

	override fun part1(lines: List<String>): Int {
		val chGrid = ChGrid(lines)
		return chGrid.yRange.asSequence()
			.flatMap { y -> chGrid.findRangeNumbers(y) }
			.filter { hr -> getPerimeter(hr).any { chGrid.isSymbol(it) } }
			.map { chGrid.extract(it).toInt() }
			.sum()
	}

	fun ChGrid.findGears() = buildList {
		forEachCh { p, ch ->
			if (ch == '*') add(p)
		}
	}

	fun ChGrid.findNumAt(p: Point): HRange? {
		val line = getLine(p.y) ?: return null
		val range = regex.findAll(line).toList().map { it.range }.firstOrNull { it.contains(p.x) } ?: return null
		return HRange(p.y, range)
	}

	override fun part2(lines: List<String>): Long {
		val chGrid = ChGrid(lines)
		val gears = chGrid.findGears()
		var sum = 0L
		gears.forEach { gear ->
			val s = buildSet {
				gear.adjacent().forEach { p ->
					val n = chGrid.findNumAt(p)
					if (n != null)
						add(n)
				}
			}
			if (s.size == 2) {
				val (n1, n2) = s.toList()
				sum += chGrid.extract(n1).toLong() * chGrid.extract(n2).toLong()
			}
		}
		return sum
	}
}