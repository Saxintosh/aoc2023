package y2023.day15

import Day

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(1320, 145) {

	fun String.theHash() = fold(0) { acc, ch -> ((acc + ch.code) * 17) % 256 }

	class Box {
		val map = mutableMapOf<String, Int>()
	}

	init {
		part1Lines { lines ->
			lines[0]
				.split(",")
				.sumOf { it.theHash() }
		}

		part2Lines { lines ->
			val boxes = Array(256) { Box() }

			lines[0]
				.splitToSequence(",")
				.forEach {
					val (label, type) = it.split("-", "=")
					val box = boxes[label.theHash()]
					if (type == "")
						box.map.remove(label)
					else
						box.map[label] = type.toInt()
				}

			boxes.mapIndexed { boxIndex, box ->
				box.map.values
					.mapIndexed { slotIndex, value -> (boxIndex + 1) * (slotIndex + 1) * value }
					.sum()
			}.sum()
		}
	}
}