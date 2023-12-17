package y2022.day03

import Day


fun main() {
	AOC
}

private object AOC : Day<Int, Int>(157, 70, 8153, 2342) {

	init {
		part1Lines { list ->
			list.sumOf {
				val len = it.length
				val a = it.substring(0, len / 2).toSet()
				val b = it.substring(len / 2).toSet()
				val x = (a intersect b).first()
				if (x.isLowerCase())
					x - 'a' + 1
				else
					x - 'A' + 27
			}
		}

		part2Lines { list ->
			list.windowed(3, 3) {
				val (l1, l2, l3) = it
				val x = ((l1.toSet() intersect l2.toSet()) intersect l3.toSet()).first()
				if (x.isLowerCase())
					x - 'a' + 1
				else
					x - 'A' + 27
			}.sum()
		}
	}
}