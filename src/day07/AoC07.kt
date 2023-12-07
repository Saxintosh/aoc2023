package day07

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Long, Long>(6440L, 5905L, 250347426L) {

	var cards = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
	var order = cards.indices.toList().map { 'Z' - it }
	var zip = cards.zip(order).toMap()

	private class Hand(hand: String, val bid: Long, val withJ: Boolean = false) : Comparable<Hand> {
		val orderedHand = hand.map { zip[it] }.joinToString("")
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
			cards = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
			order = cards.indices.toList().map { 'Z' - it }
			zip = cards.zip(order).toMap()
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