package day12

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(21, 1, 7792) {

	val pattern = Regex("#+")
	fun findGroups(s: CharArray): List<Int> {
		return pattern.findAll(s.concatToString()).toList().map { it.value.length }
	}

	fun CharArray.isValid(groups: List<Int>): Boolean {
		val srcGroup = findGroups(this)
		return srcGroup == groups
	}

	private fun generate(t: String, groups: List<Int>): List<CharArray> {
		val toResolve = mutableListOf(t.toCharArray())
		while (true) {
			val first = toResolve.first()
			val qIndex = first.indexOf('?')
			if (qIndex == -1)
				break
			toResolve.removeFirst()
			val hashVersion = first.clone().also { it[qIndex] = '#' }
			val dotVersion = first.clone().also { it[qIndex] = '.' }
			toResolve.add(hashVersion)
			toResolve.add(dotVersion)
		}
		return toResolve.filter { it.isValid(groups) }
	}

	fun parse(lines: List<String>) = lines.map { line ->
		val (template, groupsStr) = line.split(" ")
		val groups = groupsStr.split(',').map { it.toInt() }
		template to groups
	}

	override fun part1(lines: List<String>): Int {
		val src = parse(lines)
		return src.sumOf { pair ->
			val l = generate(pair.first, pair.second)
			l.size
		}
	}

	override fun part2(lines: List<String>) = 1
}