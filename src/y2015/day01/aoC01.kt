package y2015.day01

import Day


fun main() {
	AOC1
}

private object AOC1: Day<Int, Int>(3, 1, 280, 1797) {


	init {
		part1Text { txt ->
			var count = 0
			txt.forEach {
				when (it) {
					'(' -> count++
					')' -> count--
				}
			}
			count
		}

		part2Text { txt ->
			var pos = 0
			var count = 0
			for (it in txt) {
				when (it) {
					'(' -> count++
					')' -> count--
				}
				pos++
				if(count == -1)
					break
			}
			pos
		}
	}
}