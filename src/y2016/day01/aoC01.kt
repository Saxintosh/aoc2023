package y2016.day01

import Day
import kotlin.math.abs


fun main() {
	AOC1
}

private object AOC1: Day<Int, Int>(8, 4, 242, 150) {

	data class Point(val x: Int, val y: Int) {
		fun up() = Point(x, y + 1)
		fun down() = Point(x, y - 1)
		fun left() = Point(x - 1, y)
		fun right() = Point(x + 1, y)

		infix fun cabDistance(other: Point): Int = abs(other.x - x) + abs(other.y - y)
	}

	enum class Directions { North, East, South, West }

	class Turtle {
		var dir = Directions.North
		var pos = Point(0, 0)

		fun turnRight() {
			dir = when (dir) {
				Directions.North -> Directions.East
				Directions.East  -> Directions.South
				Directions.South -> Directions.West
				Directions.West  -> Directions.North
			}
		}

		fun turnLeft() {
			dir = when (dir) {
				Directions.North -> Directions.West
				Directions.West  -> Directions.South
				Directions.South -> Directions.East
				Directions.East  -> Directions.North
			}
		}

		fun walk(steps: Int) {
			val cmd: Point.() -> Point = when (dir) {
				Directions.North -> Point::up
				Directions.East  -> Point::right
				Directions.South -> Point::down
				Directions.West  -> Point::left
			}
			repeat(steps) { pos = pos.cmd() }
		}
	}

	init {
		part1Text { txt ->
			val turtle = Turtle()
			txt.split(", ").forEach {
				if (it[0] == 'R')
					turtle.turnRight()
				else
					turtle.turnLeft()
				turtle.walk(it.drop(1).toInt())
			}

			Point(0, 0) cabDistance turtle.pos
		}

		part2Text { txt ->
			val turtle = Turtle()
			var location: Point? = null
			val visited = mutableSetOf<Point>()
			visited.add(turtle.pos)
			txt.split(", ").forEach {
				if (it[0] == 'R')
					turtle.turnRight()
				else
					turtle.turnLeft()

				repeat(it.drop(1).toInt()) {
					turtle.walk(1)
					if (location == null && visited.contains(turtle.pos))
						location = turtle.pos
					visited.add(turtle.pos)
				}
			}

			Point(0, 0) cabDistance location!!
		}
	}
}