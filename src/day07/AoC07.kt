package day07

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(6440L, 5905L, 250347426L, 251224870L) {

	private class Hand(hand: String, val bid: Long, val withJ: Boolean = false) : Comparable<Hand> {
		companion object {
			var orderMap: Map<Char, Char> = emptyMap()
			fun setOrder(str: String) {
				val cardFigure = str.toList()
				val cardOrder = cardFigure.indices.toList().map { 'Z' - it }
				orderMap = cardFigure.zip(cardOrder).toMap()
			}
		}

		val orderedHand = hand.map { orderMap[it] }.joinToString("")
		val type = hand.groupingBy { it }.eachCount().toList().sortedByDescending { it.second }
			.let { pList ->
				if (!withJ)
					pList
				else {
					val theJ = pList.find { it.first == 'J' } ?: return@let pList
					val noJList = (pList - theJ).ifEmpty { listOf('A' to 0) }
					val head = noJList.first()
					val tail = noJList.drop(1)
					val newHead = head.first to (head.second + theJ.second)
					listOf(newHead) + tail
				}
			}
			.let {
				val first = it.first().second
				val second = it.getOrNull(1)?.second
				val r = when (first) {
					5    -> 7
					4    -> 6
					3    -> if (second == 2) 5 else 4
					2    -> if (second == 2) 3 else 2
					else -> 1
				}
				r
			}

		override fun compareTo(other: Hand): Int {
			val order = type - other.type
			if (order == 0)
				return orderedHand.compareTo(other.orderedHand)
			return order
		}
	}

	override fun part1(lines: List<String>) = lines
		.also {
			Hand.setOrder("AKQJT98765432")
		}
		.map {
			val s = it.split(' ')
			Hand(s[0], s[1].toLong())
		}
		.sorted()
		.mapIndexed { i, h ->
			(i + 1L) * h.bid
		}
		.sum()


	override fun part2(lines: List<String>) = lines
		.also {
			Hand.setOrder("AKQT98765432J")
		}
		.map {
			val s = it.split(' ')
			Hand(s[0], s[1].toLong(), true)
		}
		.sorted()
		.mapIndexed { i, h ->
			(i + 1L) * h.bid
		}
		.sum()
}