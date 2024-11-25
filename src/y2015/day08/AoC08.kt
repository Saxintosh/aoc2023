package y2015.day08

import Day

fun main() {
	AOC8
}


private object AOC8: Day<Int, Int>(12, 19, 1333, 2046) {

	fun String.memSize(): Int {
		var count = length - 2

		var i = 0
		while (i < length - 1) {
			if (this[i] == '\\' && this[i + 1] == '"') {
				count--
				i += 2
				continue
			}
			if (this[i] == '\\' && this[i + 1] == '\\') {
				count--
				i += 2
				continue
			}
			if (this[i] == '\\' && this[i + 1] == 'x') {
				count -= 3
				i += 4
				continue
			}
			i++
		}
		return count
	}

	fun String.rawSize() =  length  + count { it == '\\' || it == '"' } + 2


	init {
		part1Lines { lines ->
			var countRaw = 0
			var countMem = 0

			countRaw = lines.sumOf { it.length }

			lines.forEach { line ->
				countMem += line.memSize()
			}

			countRaw - countMem
		}

		part2Lines { lines ->
			var countRaw = 0
			var countMem = 0

			countMem = lines.sumOf { it.length }

			lines.forEach { line ->
				countRaw += line.rawSize()
			}

			countRaw - countMem
		}
	}
}