package y2023.day17

import Day
import java.util.PriorityQueue

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(102, 94, 1023) {

	var grid = emptyList<List<Int>>()
	var rRange = 0..0
	var cRange = 0..0

	operator fun List<List<Int>>.get(r: Int, c: Int) = this.getOrNull(r)?.getOrNull(c) ?: 0

	data class Status(val hl: Int, val r: Int, val c: Int, val dr: Int, val dc: Int, val n: Int) {
		val inRange = r in rRange && c in cRange
		fun node() = Node(r, c, dr, dc, n)
		val isStandingStill get() = dr == 0 && dc == 0
		val isAtTheDestination get() = r == rRange.last && c == cRange.last

		fun goStraight(): Status {
			val nextR = r + dr
			val nextC = c + dc
			return Status(hl + grid[nextR, nextC], nextR, nextC, dr, dc, n + 1)
		}

		fun turnTo(d: Pair<Int, Int>): Status {
			val newDr = d.first
			val newDc = d.second
			val nextR = r + newDr
			val nextC = c + newDc
			return Status(hl + grid[nextR, nextC], nextR, nextC, newDr, newDc, 1)
		}
	}

	// without heatLoss
	data class Node(val r: Int, val c: Int, val dr: Int, val dc: Int, val n: Int) {
	}

	val seen = mutableSetOf<Node>()
	val pq = PriorityQueue<Status> { a, b -> a.hl - b.hl } // min to max

	val allDirections = listOf(0 to 1, 1 to 0, 0 to -1, -1 to 0)

	fun List<String>.parse() {
		grid = this.map { line -> line.map { it.digitToInt() } }
		rRange = grid.indices
		cRange = grid[0].indices
		seen.clear()
		pq.clear()
	}


	init {
		part1Lines { lines ->
			lines.parse()

			var s = Status(0, 0, 0, 0, 0, 0)
			pq.add(s)

			while (pq.isNotEmpty()) {
				s = pq.remove()

				// if we reach the destination:
				if (s.isAtTheDestination)
					break

				val node = s.node()
				if (node in seen)
					continue

				seen.add(node)

				// keep directions
				if (s.n < 3 && !s.isStandingStill) {
					val ns = s.goStraight()
					if (ns.inRange)
						pq.add(ns)
				}

				// try other directions ( all - current - reversed)
				for (dir in (allDirections - (s.dr to s.dc) - (-s.dr to -s.dc))) {
					val ns = s.turnTo(dir)
					if (ns.inRange)
						pq.add(ns)

				}
			}

			s.hl
		}

		part2Lines { lines ->
			lines.parse()

			var s = Status(0, 0, 0, 0, 0, 0)
			pq.add(s)

			while (pq.isNotEmpty()) {
				s = pq.remove()

				// if we reach the destination:
				if (s.isAtTheDestination && s.n >= 4)
					break

				val node = s.node()
				if (node in seen)
					continue

				seen.add(node)

				// keep directions
				if (s.n < 10 && !s.isStandingStill) {
					val ns = s.goStraight()
					if (ns.inRange)
						pq.add(ns)
				}

				// try other directions ( all - current - reversed)
				if (s.n >= 4 || s.isStandingStill)
					for (dir in (allDirections - (s.dr to s.dc) - (-s.dr to -s.dc))) {
						val ns = s.turnTo(dir)
						if (ns.inRange)
							pq.add(ns)

					}
			}

			s.hl
		}
	}
}
