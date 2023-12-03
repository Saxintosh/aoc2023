package day02

import DayList
import kotlin.math.max

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Long>(8, 2286L, 2164, 69929L) {
	val limits = mapOf(
		"red" to 12,
		"green" to 13,
		"blue" to 14
	)

	override fun part1(lines: List<String>): Int = lines.sumOf { line ->
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

	override fun part2(lines: List<String>): Long = lines.sumOf { line ->
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