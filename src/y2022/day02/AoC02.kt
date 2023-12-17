package y2022.day02

import Day


fun main() {
	AOC
}

private object AOC : Day<Int, Int>(15, 12, 12794, 14979) {

	operator fun String.component1() = this[0]
	operator fun String.component2() = this[2]

	enum class Hand {
		Rock,
		Paper,
		Scissors;

		val value = ordinal + 1

		companion object {
			infix fun from(ch: Char) = when (ch) {
				'A', 'X' -> Rock
				'B', 'Y' -> Paper
				else     -> Scissors
			}
		}

		fun circularNext() = values()[(ordinal + 1) % values().size]
		fun circularPrev() = values()[(ordinal + values().size - 1) % values().size]

		fun winningHand() = circularNext()
		fun loosingHand() = circularPrev()
	}

	const val LOSE = 0
	const val DRAW = 3
	const val WIN = 6


	fun game1(a: Char, b: Char): Int {
		val elfHand = Hand from a
		val myHand = Hand from b

		val res = when {
			elfHand == myHand               -> DRAW
			elfHand.winningHand() == myHand -> WIN
			else                            -> LOSE
		}

		return res + myHand.value
	}

	fun game2(a: Char, b: Char): Int {
		val sx = Hand from a

		var res = 0

		res += when (b) {
			'X'  -> LOSE + sx.loosingHand().value
			'Y'  -> DRAW + sx.value
			else -> WIN + sx.winningHand().value
		}

		return res
	}

	init {
		part1Lines { list ->
			list.sumOf {
				val (a, b) = it
				game1(a, b)
			}
		}

		part2Lines { list ->
			list.sumOf {
				val (a, b) = it
				game2(a, b)
			}
		}
	}
}