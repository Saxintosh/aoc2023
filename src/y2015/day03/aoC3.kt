package y2015.day03

import Day


fun main() {
	AOC3
}

private data class P(val x: Int, val y: Int) {
	fun up() = P(x, y + 1)
	fun down() = P(x, y - 1)
	fun left() = P(x - 1, y)
	fun right() = P(x + 1, y)
}

private object AOC3: Day<Int, Int>(4, 3, 2572, 2631) {

	init {
		part1Text { txt ->
			var p = P(0, 0)
			val set = mutableSetOf(p)
			txt.forEach {
				p = when (it) {
					'^'  -> p.up()
					'v'  -> p.down()
					'<'  -> p.left()
					'>'  -> p.right()
					else -> TODO()
				}
				set.add(p)
			}
			set.size
		}

		part2Text { txt ->
			val set = mutableSetOf(P(0, 0))
			val santa = mutableMapOf(true to P(0,0), false to P(0,0))
			var santaTurn = true
			txt.forEach {
				var p = santa[santaTurn]!!
				p = when (it) {
					'^'  -> p.up()
					'v'  -> p.down()
					'<'  -> p.left()
					'>'  -> p.right()
					else -> TODO()
				}
				set.add(p)
				santa[santaTurn] = p
				santaTurn = !santaTurn
			}
			set.size
		}
	}
}