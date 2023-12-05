package day05

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(35L, 46L, 227653707L) {
	class MyRange(val srcRange: LongRange, val dts: Long)

	var seeds = listOf<Long>()
	var tList: MutableList<MutableList<MyRange>> = mutableListOf()

	var seeds2 = listOf<LongRange>()

	fun List<MyRange>.cvt(x: Long): Long {
		val r = firstOrNull { x in it.srcRange }
		return if (r == null) x else x - r.srcRange.first + r.dts
	}

	fun collectSeeds(lines: List<String>) {
		seeds = lines[0].substringAfter(": ").split(" ").map(String::toLong)
	}

	fun collectMaps(lines: List<String>) {
		tList = mutableListOf()
		var currentList = mutableListOf<MyRange>()
		(2..lines.indices.last).forEach {
			val parts = lines[it].split(" ")
			when (parts.size) {
				2 -> {
					currentList = mutableListOf()
					tList.add(currentList)
				}

				3 -> {
					val (d, s, l) = parts.map(String::toLong)
					currentList.add(MyRange((s until s + l), d))
				}
			}
		}
	}

	fun process(lines: List<String>) {
		collectSeeds(lines)
		collectMaps(lines)
	}

	fun collectSeeds2(lines: List<String>) {
		seeds2 = lines[0].substringAfter(": ")
			.split(" ")
			.map(String::toLong)
			.chunked(2)
			.map { it[0] until it[0] + it[1] }
	}

	fun process2(lines: List<String>) {
		collectSeeds2(lines)
		collectMaps(lines)
	}


	override fun part1(lines: List<String>): Long {
		process(lines)
		return seeds.minOf {
			tList.fold(it) { i, list -> list.cvt(i) }
		}
	}

	override fun part2(lines: List<String>): Long {
		process2(lines)
		return seeds2
			.minOf {
				it.asSequence()
					.map {
						tList.fold(it) { i, list -> list.cvt(i) }
					}
					.min()
			}
	}
}