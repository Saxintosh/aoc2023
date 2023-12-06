package day06

import DayList
import kotlin.math.floor
import kotlin.math.sqrt

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(288L, 71503L, 114400L) {

	fun parseLine(line: String) = line
		.split(":")[1]
		.split(" ")
		.filter(String::isNotBlank)
		.map(String::toLong)

	fun solver(t: Long, d: Long): LongRange {
		// ax^2 + bx + c = 0
		val a = -1.0
		val b = t.toDouble()
		val c = -d.toDouble()
		val root = sqrt(b * b - 4 * a * c)
		return listOf((-b - root) / (2 * a), (-b + root) / (2 * a))
			.sorted()
			.let {
				val first = it[0].toLong() + 1
				var last = it[1].toLong()
				if (it[1] == floor(it[1]))
					last--
				first..last
			}
	}

	override fun part1(lines: List<String>): Long {
		val timeList = parseLine(lines[0])
		val distList = parseLine(lines[1])
		val pairs = timeList.zip(distList)
		return pairs
			.map {
				val r = solver(it.first, it.second)
				r.last - r.first + 1
			}
			.fold(1) { acc, v -> acc * v }
	}

	override fun part2(lines: List<String>): Long {
		val time = lines[0].split(":")[1].replace(" ", "").toLong()
		val dist = lines[1].split(":")[1].replace(" ", "").toLong()
		val r = solver(time, dist)
		return r.last - r.first + 1
	}
}