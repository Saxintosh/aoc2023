package day15

import DayList

fun main() {
	TheDay.run()
}

private object TheDay : DayList<Int, Int>(1320, 145) {

	fun String.theHash() = fold(0) { acc, ch -> ((acc + ch.code) * 17) % 256 }

	override fun part1(lines: List<String>) = lines[0]
		.split(",")
		.sumOf { it.theHash() }

	class Box {
		val map = mutableMapOf<String, Int>()
	}

	override fun part2(lines: List<String>): Int {
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

		return boxes.mapIndexed { boxIndex, box ->
			box.map.values
				.mapIndexed { slotIndex, value -> (boxIndex + 1) * (slotIndex + 1) * value }
				.sum()
		}.sum()
	}
}
