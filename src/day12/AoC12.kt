package day12

import DayList
import Memoization

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(21, 525152, 7792, 13012052341533) {

	// add a cache for the first branch!!!
	var cache = Memoization<Pair<String, List<Int>>, Long>() {
		countPossibleArrangements(it.first, it.second)
	}

	fun parse(lines: List<String>) = lines
		.map { line ->
			val (springs, numList) = line.split(" ")
			val blockList = numList.split(',').map { it.toInt() }
			springs to blockList
		}
		.also { cache.clear() }

	// with the help of HyperNeutrino (https://www.youtube.com/watch?v=g3Ms5e7Jdqo)
	fun countPossibleArrangements(springs: String, blockList: List<Int>): Long {
		// simple checks:
		if (springs.isEmpty())
			return if (blockList.isEmpty()) 1 else 0 // empty string is only valid if no other block are expected

		if (blockList.isEmpty())
			return if ('#' in springs) 0 else 1 // if no other block are expected, is valid if no other '#' are present

		var result = 0L // now I start with no valid arrangements

		val spring = springs.first()
		val blockLen = blockList.first()

		// we have 2 cases to check!
		// first branch: spring is '.' or '?' is considered as a '.':
		if (spring in ".?") {
			// I remove the spring and proceed recursively with same blockList (and a cache)
			val nextSprings = springs.drop(1)
//			result += countPossibleArrangements(nextSprings, blockList)
//			result += cache.getOrPut(nextSprings to blockList) { countPossibleArrangements(nextSprings, blockList) }
			result += cache.getOrCompute(nextSprings to blockList)
		}

		// second branch, if '#' or if "? as a hash"
		if (spring in "#?") {
			// this mark a start of a block and I have to check if is valid. Three condition:
			if (
				springs.length >= blockLen && // 1: there must be enough springs left
				springs.take(blockLen).none { it == '.' } && // 2: must be possible to have a continues block of '#' or '?'
				(springs.getOrElse(blockLen) { '.' } != '#') // 3: no other adjacent block: the next character (if present) cannot be a '#'
			) {
				// so we can drop a block of '#'
				val nextSprings = springs.drop(blockLen + 1) // take out the block plus a '.' after the block
				val nextBlocks = blockList.drop(1)
//				result += countPossibleArrangements(nextSprings, nextBlocks)
				result += cache.getOrCompute(nextSprings to nextBlocks)
			}
		}
		return result
	}


	override fun part1(lines: List<String>) = parse(lines)
		.sumOf { countPossibleArrangements(it.first, it.second) }

	override fun part2(lines: List<String>) = parse(lines)
		.map { (s, g) ->
			listOf(s, s, s, s, s).joinToString("?") to g + g + g + g + g
		}
		.sumOf { countPossibleArrangements(it.first, it.second) }
}