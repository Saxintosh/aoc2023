package y2015.day10

import Day

fun main() {
	AOC10
}

private fun process1(a1: List<Byte>): List<Byte> {
	val a2 = mutableListOf<Byte>()
	var i = 1
	var last = a1[0]
	var count: Byte = 1
	while (i < a1.size) {
		val c = a1[i]
		if (c == last) {
			count++
		} else {
			a2.addLast(count)
			a2.addLast(last)
			last = c
			count = 1
		}
		i++
	}
	a2.addLast(count)
	a2.addLast(last)
	return a2
}

private object AOC10: Day<Int, Int>(6, 102, 492982, 6989950) {
	init {
		part1Lines { lines ->
			val iteration = lines[0].toInt()
			var num = lines[1].map { it.digitToInt().toByte() }.toList()
			repeat(iteration) { num = process1(num) }
			num.size
		}

		part2Lines { lines ->
			val iteration = lines[0].toInt() + 10
			var num = lines[1].map { it.digitToInt().toByte() }.toList()
			repeat(iteration) { num = process1(num) }
			num.size
		}
	}
}