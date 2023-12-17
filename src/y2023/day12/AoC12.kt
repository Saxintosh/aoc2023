package y2023.day12

import Day
import Memoization

fun main() {
	AOC
}

private object AOC : Day<Long, Long>(21L, 525152L, 7792L, 13012052341533L) {

	// add a cache for the first branch!!!
	val cache = Memoization<Pair<String, List<Int>>, Long> {
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
		// simple exit strategy:

		// if I evaluated all the springs:
		//    if the list of blocks are empty, I found 1 solution
		if (springs.isEmpty())
			return if (blockList.isEmpty()) 1 else 0 // empty string is only valid if no other block are expected

		// if the list of blocks is empty:
		//    the solutions is a valide one if there isn't any other block(s) '#'
		if (blockList.isEmpty())
			return if ('#' in springs) 0 else 1 // if no other block are expected, is valid if no other '#' are present

		var result = 0L // now I start with no valid arrangements

		val firstSpring = springs.first()
		val blockLen = blockList.first()

		// we have 2 cases to check!

		// First branch: firstSpring is '.' or ('?' considered as a '.'). I can take it out and iterate again:
		if (firstSpring in ".?") {
			// I remove the spring and proceed recursively with same blockList (and a cache)
			val remainingSprings = springs.drop(1)
//			result += countPossibleArrangements(nextSprings, blockList)
			result += cache.getOrCompute(remainingSprings to blockList)
		}

		// Second branch: firstSpring if '#' or ('?' considered as a '#'). I can take out a block followed by a separator '.'... and iterate again
		if (firstSpring in "#?") {
			// this mark a start of a block and (this is an optimization) I have to pre-check if it can be valid. Three condition:
			if (
				springs.length >= blockLen && // 1: there must be enough springs left to build a block
				springs.take(blockLen).none { it == '.' } && // 2: there must be possible to build a block of blockLen ('#' or '?')
				(springs.getOrElse(blockLen) { '.' } != '#') // 3: no other adjacent block: the next character (if present) cannot be a '#'
			) {
				// pre-check satisfied: so we can drop a block of '#' (or '?' as '#')
				val remainingSprings = springs.drop(blockLen + 1) // take out the block plus a '.' after the block
				val remainingBlocks = blockList.drop(1)
//				result += countPossibleArrangements(nextSprings, nextBlocks)
				result += cache.getOrCompute(remainingSprings to remainingBlocks)
			}
		}
		return result
	}

	init {
		part1Lines { lines ->
			parse(lines)
				.sumOf { countPossibleArrangements(it.first, it.second) }
		}

		part2Lines { lines ->
			parse(lines)
				.map { (s, g) ->
					listOf(s, s, s, s, s).joinToString("?") to g + g + g + g + g
				}
				.sumOf { countPossibleArrangements(it.first, it.second) }
		}
	}
}