package y2021.day04

import y2021.readInputAsSequence

class MarkBoard {
	private val markBoard = Array(5 * 5) { false }

	fun mark(row: Int, col: Int): Boolean {
		markBoard[row * 5 + col] = true
		return rowIsFull(row) || colIsFull(col)
	}

	private fun isMarked(row: Int, col: Int) = markBoard[row * 5 + col]
	fun isNotMarked(row: Int, col: Int) = !isMarked(row, col)

	private fun rowIsFull(row: Int) = (0 until 5).all { col -> isMarked(row, col) }
	private fun colIsFull(col: Int) = (0 until 5).all { row -> isMarked(row, col) }
}

class Board(private val matrix: List<List<Int>>) {
	var hasWon = false

	private val markBoard = MarkBoard()

	fun checkNum(num: Int): Boolean {
		matrix.forEachIndexed { row, rowList ->
			val col = rowList.indexOf(num)
			if (col > -1) {
				val iWon = markBoard.mark(row, col)
				if (iWon) return true
			}
		}
		return false
	}

	fun sum(): Int {
		var sum = 0
		matrix.forEachIndexed { row, nums ->
			nums.forEachIndexed { col, num ->
				if (markBoard.isNotMarked(row, col))
					sum += num
			}
		}
		return sum
	}
}

fun main() {

	var firstLine = ""
	val boards = readInputAsSequence()
		.dropWhile {
			if (firstLine == "") {
				firstLine = it
				true
			} else {
				false
			}
		}
		.filter { it.isNotBlank() }
		.chunked(5)
		.map { list -> list.map { line -> line.split(" ").mapNotNull { num -> num.toIntOrNull() } } }
		.map { Board(it) }
		.toList()


	val numbers = firstLine.split(",").map { it.toInt() }
	println(numbers)

	loopNumber@ for (num in numbers) {
		for (it in boards) {
			if (it.hasWon)
				continue
			val win = it.checkNum(num)
			if (win) {
				it.hasWon = true
				val sum = it.sum()
				if (sum > -1) {
					println("[$num, $sum] -> ${num * sum}")
//					break@loopNumber // stop after first win
				}
			}
		}
	}

}

