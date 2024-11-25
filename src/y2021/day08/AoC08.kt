package y2021.day08

import y2021.readInputAsSequence
import java.lang.Exception

object DigitDecoder {

	val map = HashMap<Set<Char>, String>()

	fun init(str: String) {
		map.clear()
		val set = str.split(" ").toSet()

		val digit1 = set.single { it.length == 2 }.toSet()
		val digit4 = set.single { it.length == 4 }.toSet()
		val digit7 = set.single { it.length == 3 }.toSet()
		val digit8 = set.single { it.length == 7 }.toSet()

		map[digit1] = "1"
		map[digit4] = "4"
		map[digit7] = "7"
		map[digit8] = "8"

		// Length:5 = 2, 3, 5
		var list235 = set.filter { it.length == 5 }.map { it.toSet() }
		val digit3 = list235.single { it.containsAll(digit1) }
		list235 = list235.minusElement(digit3)


		// Length:6 = 6, 9, 0
		var list690 = set.filter { it.length == 6 }.map { it.toSet() }
		val digit9 = list690.single { it.containsAll(digit3) }
		list690 = list690.minusElement(digit9)

		val digit0 = list690.single { it.containsAll(digit1) }
		list690 = list690.minusElement(digit0)

		val digit6 = list690.single()

		val charIn2 = digit1.minus(digit6).single()

		val digit2 = list235.single { charIn2 in it }
		list235 = list235.minusElement(digit2)

		val digit5 = list235.single()

		map[digit3] = "3"
		map[digit9] = "9"
		map[digit0] = "0"
		map[digit6] = "6"
		map[digit2] = "2"
		map[digit5] = "5"
	}

	fun decode(input: String): Int {
		return input
			.split(" ")
			.map { it.toSet() }
			.joinToString("") { map[it] ?: throw Exception("$map, $it") }
			.toInt()
	}
}

fun main() {


	val simpleList = listOf(2, 4, 3, 7)

	val count = readInputAsSequence()
		.map { it.split(" | ")[1].split(" ") }
		.sumOf {
			it.count { d -> d.length in simpleList }
		}

	println(count)

	// Part 2
	val sum = readInputAsSequence()
		.map {
			val (a, b) = it.split(" | ")
			DigitDecoder.init(a)
			DigitDecoder.decode(b)
		}
		.sum()

	println(sum)

}