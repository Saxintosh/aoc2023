package day16

import ChGrid
import DayList
import Point
import day16.TheDay.Dir.*

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(46, 51, 8323, 8491) {

	enum class Dir { Up, Down, Left, Right }
	data class MovingBeam(val point: Point, val dir: Dir) {
		fun move() = when (dir) {
			Up    -> point.up()
			Down  -> point.down()
			Left  -> point.left()
			Right -> point.right()
		}
	}

	lateinit var grid: ChGrid
	val beams = ArrayDeque<MovingBeam>()
	lateinit var heatMap: Array<Array<Boolean>>
	val visitedMB = mutableSetOf<MovingBeam>()

	fun parse(lines: List<String>) {
		grid = ChGrid(lines)
		reset()
	}

	fun reset() {
		beams.clear()
		visitedMB.clear()
		heatMap = Array(grid.yRange.last + 1) { Array(grid.xRange.last + 1) { false } }
	}

	fun markHeat(p: Point) {
		heatMap[p.y][p.x] = true
	}

	fun processCell(p: Point, dir: Dir) {
		val ch = grid[p]
		when (ch) {
			'.'  -> beams.addFirst(MovingBeam(p, dir))

			'|'  -> when (dir) {
				Up, Down    -> beams.addFirst(MovingBeam(p, dir))
				Left, Right -> {
					beams.addFirst(MovingBeam(p, Up))
					beams.addFirst(MovingBeam(p, Down))
				}
			}

			'-'  -> when (dir) {
				Left, Right -> beams.addFirst(MovingBeam(p, dir))
				Up, Down    -> {
					beams.addFirst(MovingBeam(p, Left))
					beams.addFirst(MovingBeam(p, Right))
				}
			}

			'/'  -> when (dir) {
				Right -> beams.addFirst(MovingBeam(p, Up))
				Left  -> beams.addFirst(MovingBeam(p, Down))
				Up    -> beams.addFirst(MovingBeam(p, Right))
				Down  -> beams.addFirst(MovingBeam(p, Left))
			}

			'\\' -> when (dir) {
				Right -> beams.addFirst(MovingBeam(p, Down))
				Left  -> beams.addFirst(MovingBeam(p, Up))
				Up    -> beams.addFirst(MovingBeam(p, Left))
				Down  -> beams.addFirst(MovingBeam(p, Right))
			}
		}
	}

	fun startBeam(mb: MovingBeam): Int {
		reset()
		beams.add(mb)

		while (beams.isNotEmpty()) {
			val b = beams.removeFirst()
			if (b in visitedMB)
				continue
			visitedMB.add(b)
			val p = b.move()
			if (grid.isInRange(p)) {
				markHeat(p)
				processCell(p, b.dir)
			}
		}

		return heatMap.sumOf { row -> row.count { it } }
	}

	override fun part1(lines: List<String>): Int {
		parse(lines)
		return startBeam(MovingBeam(Point(-1,0), Right))
	}

	override fun part2(lines: List<String>): Int {
		parse(lines)
		return buildList {
			grid.yRange.forEach { y ->
				add(MovingBeam(Point(-1,y), Right))
				add(MovingBeam(Point(grid.xRange.last+1,y), Left))
			}
			grid.xRange.forEach { x ->
				add(MovingBeam(Point(x,-1), Down))
				add(MovingBeam(Point(x, grid.yRange.last+1), Up))
			}
		}.maxOf {
			reset()
			startBeam(it)
		}
	}
}
