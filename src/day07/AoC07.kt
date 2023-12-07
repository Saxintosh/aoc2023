package day07

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(6440L, 5905L, 250347426L, 251224870L) {

	private class Hand(val hand: String, val bid: Long) {
		companion object {
			var orderMap: Map<Char, Char> = emptyMap()

			fun setOrder(str: String) {
				val cardFigure = str.toList()
				val cardOrder = cardFigure.indices.toList().map { 'Z' - it }
				orderMap = cardFigure.zip(cardOrder).toMap()
			}

			fun parse(line: String) = line
				.split(' ')
				.let { Hand(it[0], it[1].toLong()) }
		}

		val orderedHand = hand.map { orderMap[it] }.joinToString("")

		fun rankList(cards: String) = cards
			.groupingBy { it }
			.eachCount()
			.values
			.sortedDescending()

		fun rank(rl: List<Int>): Int {
			val first = rl.first()
			val second = rl.getOrNull(1)
			return when {
				first == 5                -> 10
				first == 4                -> 9
				first == 3 && second == 2 -> 8
				first == 3                -> 7
				first == 2 && second == 2 -> 6
				first == 2                -> 5
				else                      -> 4
			}
		}

		fun type() = rank(rankList(hand))

		fun typeWithJ(): Int {
			val jCount = hand.count { it == 'J' }
			val rl = rankList(hand.replace("J", ""))
			val nrl = listOf((rl.firstOrNull() ?: 0) + jCount) + rl.drop(1)
			return rank(nrl)
		}
	}

	override fun part1(lines: List<String>): Long {
		Hand.setOrder("AKQJT98765432")

		return lines
			.map { Hand.parse(it) }
			.sortedWith(compareBy({ it.type() }, { it.orderedHand }))
			.mapIndexed { i, h -> (i + 1L) * h.bid }
			.sum()
	}


	override fun part2(lines: List<String>): Long {
		Hand.setOrder("AKQT98765432J")

		return lines
			.map { Hand.parse(it) }
			.sortedWith(compareBy({ it.typeWithJ() }, { it.orderedHand }))
			.mapIndexed { i, h -> (i + 1L) * h.bid }
			.sum()
	}
}