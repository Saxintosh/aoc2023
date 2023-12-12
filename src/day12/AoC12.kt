package day12

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(21, 525152, 7792) {

	fun parse(lines: List<String>) = lines.map { line ->
		val (springs, numList) = line.split(" ")
		val blocks = numList.split(',').map { it.toInt() }
		springs to blocks
	}

	// with the help of HyperNeutrino (https://www.youtube.com/watch?v=g3Ms5e7Jdqo)
	fun countArrangements(springs: String, blocks: List<Int>): Int {
		// simple checks:
		if (springs.isEmpty())
			return if (blocks.isEmpty()) 1 else 0 // empty string is only valid if no other block are expected

		if (blocks.isEmpty())
			return if ('#' in springs) 0 else 1 // if no other block are expected, is valid if no other '#' are present

		var result = 0 // now I start with no valid arrangements

		// we have 2 cases to check!
		// first branch, if '.' or if "? as a dot": I remove a spring and proceed to check a block of '#' with the same control
		if (springs[0] in ".?")
			result += countArrangements(springs.drop(1), blocks)

		// second branch, if '#' or if "? as a hash"
		if (springs[0] in "#?") {
			val blockLen = blocks[0]
			// this mark a start of a block and I have to check if is valid. Three condition:
			if (
				springs.length >= blockLen && // 1: there must be enough springs left
				'.' !in springs.take(blockLen) && // 2: must be a continues block of '#' or '?'
				( // 3: no other adjacent block:
						springs.length == blockLen || // no more springs is ok
								springs[blockLen] != '#' // next char cannot be the start of another block
						)
			) {
				// so we can start a valid block
				val nextSprings = springs.drop(blockLen + 1) // take out the block and a '.' after the block
				val nextControl = blocks.drop(1)
				result += countArrangements(nextSprings, nextControl)
			}
		}
		return result
	}


	override fun part1(lines: List<String>) = parse(lines)
		.sumOf { countArrangements(it.first, it.second) }

	override fun part2(lines: List<String>) = parse(lines)
		.map { (s, g) ->
			listOf(s, s, s, s, s).joinToString("?") to g + g + g + g + g
		}
		.sumOf { countArrangements(it.first, it.second) }
}