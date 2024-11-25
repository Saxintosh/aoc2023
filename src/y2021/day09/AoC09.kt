package y2021.day09

import y2021.readInputAsSequence

fun main() {
	var firstProcessed = false
	var middle: String? = null
	var last: String? = null

	fun minValue(a: String, b: String, c: String, pos: Int): Int {
		val up = a.getOrNull(pos) ?: '9'
		val down = c.getOrNull(pos) ?: '9'
		val left = b.getOrNull(pos - 1) ?: '9'
		val right = b.getOrNull(pos + 1) ?: '9'
		val me = b[pos]

		val isMin = me < up && me < down && me < left && me < right
		return if (isMin) me.digitToInt() + 1 else 0
	}

	fun sum(a: String, b: String, c: String): Int {
		var sum = 0
		b.forEachIndexed { index, _ ->
			sum += minValue(a, b, c, index)
		}
		return sum
	}

	var somma = readInputAsSequence()
		.windowed(3)
		.sumOf { (a, b, c) ->
			val row1 = if (!firstProcessed) {
				firstProcessed = true
				sum("", a, b)
			} else 0

			val row2 = sum(a, b, c)

			middle = b
			last = c

			row1 + row2
		}

	somma += sum(middle!!, last!!, "")

	println(somma)
}