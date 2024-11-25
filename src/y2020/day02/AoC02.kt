package y2020.day02

import y2020.readInputAsList
import y2020.readTestAsList

fun check1(range: ClosedRange<Int>, ch: Char, pw: String) = pw.count { it == ch } in range
fun check2(range: ClosedRange<Int>, ch: Char, pw: String): Boolean {
	val c1 = pw.getOrNull(range.start-1)
	val c2 = pw.getOrNull(range.endInclusive-1)
	return (c1 == ch || c2 == ch) && (c1 != c2)
}


fun main() {
	fun part1(list: List<String>): Int {
		val l = list.map {
			val (x1, pw) = it.split(":")
			val (x2, ch) = x1.split(" ")
			val (min, max) = x2.split("-")
			check1(min.toInt()..max.toInt(), ch[0], pw.trim())
		}
		return l.count { it }
	}

	fun part2(list: List<String>): Int {
		val l = list.map {
			val (x1, pw) = it.split(":")
			val (x2, ch) = x1.split(" ")
			val (min, max) = x2.split("-")
			check2(min.toInt()..max.toInt(), ch[0], pw.trim())
		}
		return l.count { it }
	}

	// test if implementation meets criteria from the description, like:
	val testInput = readTestAsList()
	val input = readInputAsList()

	check(part1(testInput) == 2)
	println(part1(input))

	check(part2(testInput) == 1)
	println(part2(input))
}