package y2021.day10

import y2021.readInputAsSequence
import y2021.readTestAsSequence

fun main() {

	val openP = listOf('(', '{', '[', '<')
	val errorScore = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
	val secondScore = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

	fun check(txt: String): Int {
		val queue = mutableListOf<Char>()
		fun Char.match() = when (queue.lastOrNull()) {
			'('  -> this == ')'
			'['  -> this == ']'
			'{'  -> this == '}'
			'<'  -> this == '>'
			else -> false
		}
		for (c in txt) {
			when {
				c in openP -> queue.addLast(c)
				c.match()  -> queue.removeLast()
				else       -> return errorScore[c]!!
			}
		}
		return 0
	}

	fun check2(txt: String): Long {
		val queue = mutableListOf<Char>()
		fun Char.match() = when (queue.lastOrNull()) {
			'('  -> this == ')'
			'['  -> this == ']'
			'{'  -> this == '}'
			'<'  -> this == '>'
			else -> false
		}
		for (c in txt) {
			when {
				c in openP -> queue.addLast(c)
				c.match()  -> queue.removeLast()
				else       -> return -1L
			}
		}

		var totalScore = 0L
		queue.toList().reversed().forEach { c ->
			totalScore *= 5
			totalScore += secondScore[c]!!
		}
		return totalScore
	}


	val s1 = readInputAsSequence()
		.sumOf { check(it) }

	println("part 1 $s1")

	val s2 = readInputAsSequence()
		.map { check2(it) }
		.filter { it > -1 }
		.sorted()
		.toList()
		.run {
			val i = size / 2
			get(i)
		}

	println("part 2 $s2")

}