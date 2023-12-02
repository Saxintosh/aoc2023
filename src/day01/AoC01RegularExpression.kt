package day01

import DayList


fun main() {
	TheDay2.run()
}


/**
 * To find the last match, I can search all the matches and get the last one but... it
 * doesn't work because the list of matches doesn't include the overlapping text:
 * in "seveneightwo" it only finds seven and eight but not two (with the overlapping t).
 *
 * You have to use a look ahead expression (?=(one|two|...)) which however returns a list
 * of matchGroups where each matchGroup is a couple of matches where the first is always
 * the empty string....
 *
 * This code is slower than the other one.
 */

private object TheDay2 : DayList<Int, Int>(142, 281) {

	val rList = listOf("[1-9]", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
	val rListStr = rList.joinToString("|")

	// with lookahead
	val regex = "(?=($rListStr))".toRegex()


	override fun part1(lines: List<String>): Int = lines.sumOf { line ->
		val digits = line.filter { it.isDigit() }
		(digits.first().toString() + digits.last()).toInt()
	}

	fun parse(mr: MatchGroup?): Int {
		val v = mr!!.value
		return if (v.length == 1)
			v.toInt()
		else
			rList.indexOf(v)
	}

	override fun part2(lines: List<String>): Int = lines.sumOf { line ->
		val m = regex.findAll(line).toList().map { it.groups.last() }
		val first = parse(m.first())
		val last = parse(m.last())

		first * 10 + last
	}

}