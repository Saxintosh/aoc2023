package y2022.day05

import Day


fun main() {
	AOC
}

private object AOC : Day<String, String>("CMZ", "MCD", "QMBMJDFTD", "NBTVTJNFJ") {
	class Cmd(txt: String) {
		val qty: Int
		val c1: Int
		val c2: Int

		init {
			val s = txt.split(" ")
			qty = s[1].toInt()
			c1 = s[3].toInt() - 1
			c2 = s[5].toInt() - 1
		}

		companion object {
			fun fromTxt(txt: String) = txt.lines().map { Cmd(it) }
		}
	}

	class Stacks(txt: String) {
		private val stacks: Array<MutableList<Char>>

		init {
			val l = txt.lines()
			val last = l.last()
			val count = last.split("   ").last().trim().toInt()

			stacks = Array(count) { mutableListOf() }
			l.dropLast(1).reversed().forEach { line ->
				(0 until count).forEach { index ->
					val ch = line[index * 4 + 1]
					if (ch != ' ')
						stacks[index].add(ch)
				}
			}
		}

		fun execute1(cmd: Cmd) {
			repeat(cmd.qty) {
				val last = stacks[cmd.c1].removeLast()
				stacks[cmd.c2].add(last)
			}
		}

		fun execute2(cmd: Cmd) {
			val x = stacks[cmd.c1].takeLast(cmd.qty)
			repeat(cmd.qty) { stacks[cmd.c1].removeLast() }
			stacks[cmd.c2].addAll(x)
		}

		fun getTop() = String(stacks.map { it.last() }.toCharArray())
	}

	init {
		part1Text { txt ->
			val (a,b) = txt.split("\n\n")
			val stacks = Stacks(a)
			val cmdList = Cmd.fromTxt(b)
			cmdList.forEach { stacks.execute1(it) }
			stacks.getTop()
		}

		part2Text { txt ->
			val (a,b) = txt.split("\n\n")
			val stacks = Stacks(a)
			val cmdList = Cmd.fromTxt(b)
			cmdList.forEach { stacks.execute2(it) }
			stacks.getTop()
		}
	}
}
