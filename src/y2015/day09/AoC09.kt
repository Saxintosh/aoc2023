package y2015.day09

import Day
import permutations
import java.util.HashMap

fun main() {
	AOC9
}

private class Map: HashMap<Pair<String, String>, Int>() {
	val names = HashSet<String>()

	fun set(n1: String, n2: String, d: Int) {
		names.add(n1)
		names.add(n2)

		if (n1 < n2)
			this[n1 to n2] = d
		else
			this[n2 to n1] = d
	}

	fun get(n1: String, n2: String): Int {
		return if (n1 < n2)
			this[n1 to n2]!!
		else
			this[n2 to n1]!!
	}

}

private object AOC9: Day<Int, Int>(605, 982, 207, 804) {

	fun String.parse(m: Map) {
		val s = this.split(" = ")
		val dist = s[1].toInt()
		val (n1, n2) = s[0].split(" to ")
		m.set(n1, n2, dist)
	}

	init {
		part1Lines { lines ->
			val m = Map()
			lines.forEach { line ->
				line.parse(m)
			}

			permutations(m.names.toList()).minOf {
				it.windowed(2, 1).sumOf { m.get(it[0], it[1]) }
			}
		}

		part2Lines { lines ->
			val m = Map()
			lines.forEach { line ->
				line.parse(m)
			}

			permutations(m.names.toList()).maxOf {
				it.windowed(2, 1).sumOf { m.get(it[0], it[1]) }
			}
		}
	}
}