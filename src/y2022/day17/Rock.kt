package aoc2022.day17


class Rock(val rows: List<CharArray>) {
	val h = rows.size
	val w = rows[0].size

	fun reversedList(wide: Int): List<CharArray> {
		val suffix = CharArray(wide - w) { '.' }
		return rows.reversed().map { it.plus(suffix) }
	}

	fun rightCheckPoints(rHPos: Int) =
		rows.mapIndexed { idx, row ->
			row.lastIndexOf('#') + 1 to idx
		}

	fun leftCheckPoints(rHPos: Int) =
		rows.mapIndexed { idx, row ->
			row.indexOf('#') - 1 to idx
		}

	fun downCheckPoints(rHPos: Int) =
		(0 until w).map { x ->
			x to (0 until  h).reversed().first { y -> rows[y][x] == '#' } + 1
		}

	companion object {
		private val rocksTxt = """
	####
	
	.#.
	###
	.#.
	
	..#
	..#
	###
	
	#
	#
	#
	#
	
	##
	##
""".trimIndent()

		val rocks = rocksTxt
			.split("\n\n")
			.map { chunk -> Rock(chunk.lines().map { it.toCharArray() }) }
	}
}