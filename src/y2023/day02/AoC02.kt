package y2023.day02

import Day
import kotlin.math.max

fun main() {
	AOC
}

private object AOC : Day<Int, Long>(8, 2286L, 2164, 69929L) {

	val limits = mapOf(
		"red" to 12,
		"green" to 13,
		"blue" to 14
	)

	init {
		part1Lines { lines ->
			lines.sumOf { line ->
				val (gameStr, setsStr) = line.split(": ")
				val game = gameStr.split(" ")[1].toInt()
				val good = setsStr.split("; ").all { setStr ->
					setStr.split(", ").all { color ->
						val (v, k) = color.split(" ")
						limits.getValue(k) >= v.toInt()
					}
				}
				if (good)
					game
				else
					0
			}
		}

		part2Lines { lines ->
			lines.sumOf { line ->
				val h = HashMap<String, Long>()
				line.substringAfter(": ")
					.split("; ")
					.forEach { set ->
						set.split(", ")
							.forEach { color ->
								val (v, k) = color.split(" ")
								h[k] = max(h[k] ?: 0, v.toLong())
							}
					}
				h.getValue("red") * h.getValue("green") * h.getValue("blue")
			}
		}
	}
}