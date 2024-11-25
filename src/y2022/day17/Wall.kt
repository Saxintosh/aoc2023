package aoc2022.day17

class Wall(val wide: Int, lines: List<String>) {
	val jetStream = lines[0]
	var jetPos = jetStream.indices.last
	val stack = mutableListOf<CharArray>()
	var rIdx = Rock.rocks.indices.last
	var rock: Rock = Rock.rocks.last()
	var rHPos = 0
	var rVPos = 0 // from the top

	fun getJetDirection() = jetStream[++jetPos % jetStream.length]

	fun debug() {
		repeat(10) {
			println()
		}
		stack.asSequence()
		for (idx in stack.indices.reversed())
			println('|' + String(stack[idx]) + '|')
		print('+' + String(CharArray(wide) { '-' }) + '+')
	}

	private fun List<Pair<Int, Int>>.toChars(): List<Char> {
		return this.map { (x, y) ->
			stack.getOrNull(stack.indices.last - y - rVPos)?.getOrNull(x + rHPos) ?: '#'
		}
	}

	private fun selectNextRock() {
		rock = Rock.rocks[++rIdx % Rock.rocks.size]
	}

	private fun paintRock(paint: Char) {
		var y = stack.indices.last - rVPos
		for (row in rock.rows) {
			for (i in row.indices) {
				val x = rHPos + i
				if (row[i] == '#')
					stack[y][x] = paint
			}
			y--
		}
	}

	private fun putRock() = paintRock('#')
	private fun removeRock() = paintRock('.')


	private fun moveRight() {
		val isFreeToMove = rock.rightCheckPoints(rHPos).toChars().all { it == '.' }
		if (isFreeToMove) {
			removeRock()
			rHPos++
			putRock()
		}
	}

	private fun moveLeft() {
		val isFreeToMove = rock.leftCheckPoints(rHPos).toChars().all { it == '.' }
		if (isFreeToMove) {
			removeRock()
			rHPos--
			putRock()
		}
	}

	private fun moveDown(): Boolean {
		val isFreeToMove = rock.downCheckPoints(rHPos).toChars().all { it == '.' }
		if (isFreeToMove) {
			removeRock()
			rVPos++
			putRock()
		}
		return isFreeToMove
	}


	private fun putNewRock() {
		selectNextRock()
		rVPos = 0
		repeat(3 + rock.h) {
			stack.add(CharArray(wide) { '.' })
		}
		rHPos = 2
		putRock()
	}

	fun dropNewRock() {
		putNewRock()
		do {
			when (getJetDirection()) {
				'<' -> moveLeft()
				'>' -> moveRight()
			}
		} while (moveDown())
		while (stack.last().all { it == '.' })
			stack.removeLast()
	}
}
