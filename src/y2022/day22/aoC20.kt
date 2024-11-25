package y2022.day22

import download
import readLines

private inline val UNEXPECTED: Nothing get() =  throw Error("Unexpected status!")

private data class P(val x: Int, val y: Int) {
	fun up() = P(x, y - 1)
	fun down() = P(x, y + 1)
	fun left() = P(x - 1, y)
	fun right() = P(x + 1, y)

	fun wrap(w: Int, h: Int) = P(x.mod(w), y.mod(h))
	override fun toString() = "($x,$y)"
}

private class MyMap(val l: List<String>) {
	var is3DWrap = false
	var facing = 0 // 0 = right, 1 = down, 2 = left, 3 = up
	val side = l.size / 3
	val r1 = (0 until side)
	val r2 = (side until 2 * side)
	val r3 = (2 * side until 3 * side)
	val r4 = (3 * side until 4 * side)

	var myP = P(l[0].indexOfFirst { it == '.' }, 0)

	val P.wrap: P get() = this.wrap(4 * side, 3 * side)
	val P.wrap3D: P
		get() = when {
			x == -1       -> {
				// left
				when (y) {
					in r1 -> rotateY(rot = 'L', xRange = r2, newY = r2.first)
					in r2 -> rotateY(rot = 'R', xRange = r4.reversed(), newY = r3.last)
					in r3 -> rotateY(rot = 'R', xRange = r2.reversed(), newY = r2.last)
					else  -> UNEXPECTED
				}
			}

			x == 4 * side -> {
				// right
				when (y) {
					in r1 -> translateY(yRange = r3.reversed(), newX = r4.last)
					in r2 -> rotateY(rot = 'R', xRange = r4.reversed(), newY = r3.first)
					in r3 -> translateY(yRange = r1.reversed(), newX = r3.last)
					else  -> UNEXPECTED
				}
			}

			y == -1       -> {
				// up
				when (x) {
					in r1 -> translateX(xRange = r3.reversed(), newY = r1.first)
					in r2 -> rotateX(rot = 'R', yRange = r1, newX = r3.first)
					in r3 -> translateX(xRange = r1.reversed(), newY = r2.first)
					in r4 -> rotateX(rot = 'L', yRange = r2.reversed(), newX = r3.last)
					else  -> UNEXPECTED
				}
			}

			y == 3 * side -> {
				// down
				when (x) {
					in r1 -> translateX(xRange = r3.reversed(), newY = r3.last)
					in r2 -> rotateX(rot = 'L', yRange = r3.reversed(), newX = r3.first)
					in r3 -> translateX(xRange = r1.reversed(), newY = r2.last)
					in r4 -> rotateX(rot = 'L', yRange = r2.reversed(), newX = r1.first)
					else  -> UNEXPECTED
				}
			}

			else          -> UNEXPECTED
		}

	private fun P.rotateY(rot: Char, xRange: IntProgression, newY: Int): P {
		turn(rot)
		var newX = y % side
		if (xRange.step == 1)
			newX += xRange.first
		else
			newX = xRange.last - newX

		return P(newX, newY)
	}

	private fun P.rotateX(rot: Char, yRange: IntProgression, newX: Int): P {
		turn(rot)
		var newY = x % side
		if (yRange.step == 1)
			newY += yRange.first
		else
			newY = yRange.last - newY

		return P(newX, newY)
	}

	private fun P.translateY(yRange: IntProgression, newX: Int): P {
		turn('R')
		turn('R')
		var newY = y % side
		if (yRange.step == 1)
			newY += yRange.first
		else
			newY = yRange.last - newY

		return P(newX, newY)
	}

	private fun P.translateX(xRange: IntProgression, newY: Int): P {
		turn('R')
		turn('R')
		var newX = x % side
		if (xRange.step == 1)
			newX += xRange.first
		else
			newX = xRange.last - newX

		return P(newX, newY)
	}

	operator fun get(p: P) = l.getOrNull(p.y)?.getOrNull(p.x) ?: ' '

	fun turn(ch: Char) = when (ch) {
		'R'  -> facing = (facing + 1).mod(4)
		'L'  -> facing = (facing - 1).mod(4)
		'I'  -> facing = (facing + 2).mod(4) // invert
		else -> UNEXPECTED
	}

	private tailrec fun nextP(p: P): P {
		var newP = when (facing) {
			0    -> p.right()
			1    -> p.down()
			2    -> p.left()
			3    -> p.up()
			else -> UNEXPECTED
		}

		if (is3DWrap)
			newP = newP.wrap3D
		else
			newP = newP.wrap

		val ch = this[newP]
		return if (ch == ' ')
			nextP(newP)
		else
			newP
	}

	fun step() {
		val p = nextP(myP)
		when (this[p]) {
			'.'  -> myP = p
			'#'  -> Unit
			else -> UNEXPECTED
		}
	}

	fun process(cmd: String) {
		var str = cmd
		while (str.isNotEmpty()) {
			if (str[0] == 'R' || str[0] == 'L') {
				turn(str[0])
				str = str.drop(1)
			} else {
				val i = str.indexOfFirst { it == 'R' || it == 'L' }
				val steps: Int
				if (i == -1) {
					steps = str.toInt()
					str = ""
				} else {
					steps = str.substring(0, i).toInt()
					str = str.substring(i, str.length)
				}
				repeat(steps) { step() }
			}
		}
	}
}

fun main() {

	download()


	fun part1(lines: List<String>): Int {
		val cmd = lines.last()
		val m = MyMap(lines.dropLast(2))
		m.process(cmd)
		return 1000 * (m.myP.y + 1) + 4 * (m.myP.x + 1) + m.facing
	}

	fun part2(lines: List<String>): Int {
		val cmd = lines.last()
		val m = MyMap(lines.dropLast(2))
		m.is3DWrap = true
		m.process(cmd)
		return 1000 * (m.myP.y + 1) + 4 * (m.myP.x + 1) + m.facing
	}


	readLines(6032, 5031, ::part1, ::part2)
}