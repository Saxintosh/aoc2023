package day01

import DayList


fun main() {
	TheDay2.run()
}

/**
 * To find the last match, I can search them all and then get the last one but... it doesn't work
 * because the match list doesn't contemplate the overlapped text: in "seveneightwo" it only finds
 * seven & eight but not two (with the overlapping t)
 *
 * One idea is to reverse the strings and matches and then search for the first match...
 */


private object TheDay2 : DayList<Int, Int>(142, 281) {

	val rList = listOf("[1-9]", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
	val regex = rList.joinToString("|").toRegex()

	val rListReversed = listOf("[1-9]") + listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine").map { it.reversed() }
	val reversedRegex = rListReversed.joinToString("|").toRegex()


	override fun part1(lines: List<String>): Int = lines.sumOf { line ->
		val digits = line.filter { it.isDigit() }
		(digits.first().toString() + digits.last()).toInt()
	}

	fun findFirst(line: String): String {
		val v = regex.find(line)?.value!!
		return if (v.length == 1)
			v
		else
			"${rList.indexOf(v)}"
	}

	fun findLast(line: String): String {
		val v = reversedRegex.find(line.reversed())?.value!!
		return if (v.length == 1)
			v
		else
			"${rListReversed.indexOf(v)}"
	}

	override fun part2(lines: List<String>): Int = lines.sumOf { line ->
		val digits = findFirst(line) + findLast(line)
		digits.toInt()
	}

}