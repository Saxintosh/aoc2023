package y2015.day07

import Day

fun main() {
	AOC7
}


private object AOC7: Day<Int, Int>(1, 1, 46065, 14134) {


	fun String.parse(): Gate {
		val (p, target) = this.split(" -> ")
		val parts = p.split(" ")

		return when (parts.size) {
			1    -> GSet(Source.from(parts[0]), target)
			2    -> GNot(Source.from(parts[1]), target)
			3    -> when (parts[1]) {
				"AND"    -> GAnd(Source.from(parts[0]), Source.from(parts[2]), target)
				"OR"     -> GOr(Source.from(parts[0]), Source.from(parts[2]), target)
				"RSHIFT" -> GRShift(Source.from(parts[0]), parts[2].toInt(), target)
				"LSHIFT" -> GLShift(Source.from(parts[0]), parts[2].toInt(), target)
				else     -> TODO()
			}

			else -> TODO()
		}
	}

	init {
		part1Lines { lines ->
			val mem = Memory()

			var queue = lines.map { it.parse() }

			while (queue.isNotEmpty()) {
				queue = mem.process(queue)
			}

			mem["a"]!!
		}



		part2Lines { lines ->
			val mem = Memory()

			var queue = lines.map { it.parse() }

			queue = mem.process(queue)

			mem["b"] = realRes1!!

			while (queue.isNotEmpty()) {
				queue = mem.process(queue)
			}

			mem["a"]!!
		}
	}
}