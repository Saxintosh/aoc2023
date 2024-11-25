package y2021.day02

import y2021.readInputAsSequence

fun main() {

	var hPos = 0
	var depth = 0

	readInputAsSequence()
		.map { it.split(' ') }
		.map { (s, i) -> s to i.toInt() }
		.forEach { (x, qty) ->
			when (x) {
				"forward" -> hPos += qty
				"down"    -> depth += qty
				"up"      -> depth -= qty
			}
		}

	println(hPos * depth)

	hPos = 0
	depth = 0
	var aim = 0

	readInputAsSequence()
		.map { it.split(' ') }
		.map { (s, i) -> s to i.toInt() }
		.forEach { (x, qty) ->
			when (x) {
				"forward" -> {
					hPos += qty
					depth += aim * qty
				}
				"down"    -> aim += qty
				"up"      -> aim -= qty
			}
		}

	println(hPos * depth)

}