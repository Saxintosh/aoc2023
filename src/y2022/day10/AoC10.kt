package y2022.day10

import Day

sealed class Instruction {
	companion object {
		fun from(line: String): Instruction {
			val s = line.split(" ")
			return when (s[0]) {
				"noop" -> Noop
				"addx" -> AddX(s[1].toInt())
				else   -> TODO()
			}
		}
	}
}

data object Noop : Instruction()
class AddX(val n: Int) : Instruction()

class Cpu(private val prg: List<String>, val peekBlock: Cpu.() -> Unit) {
	var x = 1
	var cycle = 0

	// Current instruction register
	private var cir: Instruction = Noop

	// Program Counter
	private var pc = 0

	private fun doCycle() {
		cycle++
		peekBlock(this)

		when (val thisCir = cir) {
			Noop    -> cir = Instruction.from(prg[pc++])
			is AddX -> x += thisCir.n.also { cir = Noop }
		}
	}

	fun run() {
		while (pc < prg.size)
			doCycle()
	}
}

class Crt {
	private val pixels = Array(6) { CharArray(40) { ' ' } }

	private fun toRowCol(cycle: Int): Pair<Int, Int> {
		val pos = (cycle - 1) % 240
		val row = pos / 40
		val col = pos % 40
		return row to col
	}

	fun printScreen() {
		pixels.forEach {
			println(String(it))
		}
		println()
	}

	fun draw(cycle: Int, x: Int) {
		val xx = x % 40
		val xr = xx - 1..xx + 1
		val (row, col) = toRowCol(cycle)
		if (col in xr)
			pixels[row][col] = '#'
		else
			pixels[row][col] = '.'
	}
}


fun main() {
	AOC
}

private object AOC : Day<Int, Int>(13140, 1) {

	init {
		part1Lines { lines ->
			var signalStrength = 0
			Cpu(lines) {
				if (cycle % 40 == 20)
					signalStrength += cycle * x
			}.run()

			 signalStrength
		}

		part2Lines { lines ->
			val crt = Crt()
			Cpu(lines) {
				crt.draw(cycle, x)
			}.run()
			crt.printScreen()
			1
		}
	}
}