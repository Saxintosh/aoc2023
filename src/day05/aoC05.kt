package day05

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(35L, 46L, 227653707L, 78775051L) {
	class MyRange(val dst: Long, val src: Long, val len: Long) {
		val rSrc = (src until src + len)
		val rDst = (dst until dst + len)
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


	fun collectBlocks(lines: List<String>) = lines
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
		}

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

	override fun part2(lines: List<String>): Long {
		val seeds = collectSeedsRange(lines)
		val blocks = collectBlocks(lines)
		return seeds.parallelStream()
			.map { lRange ->
				lRange.asSequence()
					.map {
						blocks.fold(it) { i, list -> list.cvt(i) }
					}
					.min()
			}
			.toList()
			.min()
	}
}