package y2022.day12

import download
import readLines

data class P(val x: Int, val y: Int) {
	fun up() = P(x + 1, y)
	fun down() = P(x - 1, y)
	fun left() = P(x, y - 1)
	fun right() = P(x, y + 1)
	override fun toString() = "($x,$y)"
}

class XYMap(lines: List<String>) {
	private val start: P
	private val end: P
	private val grid: List<String>

	init {
		val ys = lines.indexOfFirst { it.contains("S") }
		val xs = lines[ys].indexOf('S')
		start = P(xs, ys)
		val ye = lines.indexOfFirst { it.contains("E") }
		val xe = lines[ye].indexOf('E')
		end = P(xe, ye)
		grid = lines.map { it.replace("S", "a").replace("E", "z") }
	}

	operator fun get(p: P) = grid.getOrNull(p.y)?.getOrNull(p.x)

	private fun findPossibleSteps(p: P) = buildList {
		val ch = get(p)!! + 1
		fun addIfGood(aP: P) = get(aP)?.takeIf { it <= ch }?.let { add(aP) }
		addIfGood(p.up())
		addIfGood(p.left())
		addIfGood(p.down())
		addIfGood(p.right())
	}

	fun findAs() = buildList {
		grid.forEachIndexed { y, row ->
			row.forEachIndexed { x, c ->
				if (c == 'a')
					add(P(x, y))
			}
		}
	}

	class APath(val list: List<P>) {
		constructor(p: P) : this(listOf(p))

		fun plus(p: P) = APath(list.plus(p))
		fun last() = list.last()
		operator fun contains(p: P) = p in list
		val size get() = list.size
		override fun toString() = list.toString()
	}

	fun find(startP: P = start): APath? {
		var pathsToTry = listOf(APath(startP))
		var goodPath: APath? = null
		var goodSize = Int.MAX_VALUE
		val visitedPs = mutableSetOf<P>()

		while (pathsToTry.isNotEmpty()) {
			val newPathsToTry = mutableListOf<APath>()
			for (path in pathsToTry) {
				val steps = findPossibleSteps(path.last())
				for (p in steps) {
					if (p in visitedPs)
						continue
					val newPath = path.plus(p)
					when {
						newPath.size >= goodSize -> Unit
						p == end                 -> {
							goodPath = newPath
							goodSize = newPath.size
						}

						else                     -> {
							val oldPath = newPathsToTry.firstOrNull { p in it }
							if (oldPath != null) {
								if (oldPath.size > newPath.size) {
									newPathsToTry.remove(oldPath)
									newPathsToTry.add(newPath)
								}
							} else {
								newPathsToTry.add(path.plus(p))
							}
						}
					}
				}
				visitedPs.add(path.last())
			}
			pathsToTry = newPathsToTry
		}

		return goodPath
	}

}

fun main() {

	download()

	fun part1(lines: List<String>): Int {
		val m = XYMap(lines)
		return m.find()!!.size - 1
	}

	fun part2(lines: List<String>): Int {
		val m = XYMap(lines)
		return m.findAs().mapNotNull { m.find(it) }.minBy { it.size }.size - 1
	}


	readLines(31, 29, ::part1, ::part2)
}