package day05

import DayList
import kotlin.math.max
import kotlin.math.min

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(35L, 46L, 227653707L, 78775051L) {
	class MyRange(val dst: Long, val src: Long, val len: Long) {
		val rSrc = (src until src + len)
		val delta get() = dst - src
		operator fun contains(x: Long) = x in rSrc
		override fun toString() = "$src->$dst($len)"
		fun cvt(x: Long) = x + delta
	}

	fun List<MyRange>.cvt(x: Long) = firstOrNull { x in it }?.cvt(x) ?: x

	fun collectSeeds(lines: List<String>) = lines[0]
		.substringAfter(": ")
		.split(" ")
		.map(String::toLong)


	fun collectBlocks(lines: List<String>): List<List<MyRange>> = lines
		.drop(3)
		.fold(mutableListOf(mutableListOf<MyRange>())) { acc, line ->
			val parts = line.split(" ")
			when (parts.size) {
				1 -> acc.add(mutableListOf())
				3 -> {
					val (d, s, l) = parts.map(String::toLong)
					acc.last().add(MyRange(d, s, l))
				}
			}
			acc
		}.onEach { rList -> rList.sortBy { it.src } }

	fun collectSeedsRange(lines: List<String>) = lines[0].substringAfter(": ")
		.split(" ")
		.map(String::toLong)
		.chunked(2)
		.map { it[0] until it[0] + it[1] }


	override fun part1(lines: List<String>): Long {
		val seeds = collectSeeds(lines)
		val blocks = collectBlocks(lines)
		return seeds.minOf {
			blocks.fold(it) { i, list -> list.cvt(i) }
		}
	}

	// 5m 20s
//	override fun part2(lines: List<String>): Long {
//		val seeds = collectSeedsRange(lines)
//		val blocks = collectBlocks(lines)
//		return seeds.parallelStream()
//			.map { lRange ->
//				lRange.asSequence()
//					.map {
//						blocks.fold(it) { i, list -> list.cvt(i) }
//					}
//					.min()
//			}
//			.toList()
//			.min()
//	}

	// 1ms
	override fun part2(lines: List<String>): Long {
		var ranges = collectSeedsRange(lines)
		val blocks = collectBlocks(lines)
		blocks.forEach { rList ->
			val newRanges = mutableListOf<LongRange>()
			ranges.forEach { range ->
				var min = range.first
				val max = range.last
				for (r in rList) {
					if (r.rSrc.last < min)
						continue
					if (r.rSrc.first > max)
						break
					if (min < r.rSrc.first) {
						newRanges.add(min until r.rSrc.first)
						min = r.rSrc.first
					}
					val bottom = max(min, r.rSrc.first)
					val top = min(max, r.rSrc.last)
					newRanges.add((bottom + r.delta) .. (top + r.delta))
					min = top + 1
					continue
				}
				if (min < max)
					newRanges.add(min .. max)
			}
			ranges = newRanges
		}
		return ranges.minOf { it.first }
	}
}