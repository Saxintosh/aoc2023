package y2022.day13

import download
import readLines

fun main() {

	download()

	fun String.isAnInteger() = firstOrNull()?.isDigit() == true
	fun String.isAList() = firstOrNull() == '['
	fun String.isEndOfList() = firstOrNull() == ']'
	fun String.firstElement() = split(",", "]", limit = 2)[0]
	fun String.dropFirstElement(): String {
		var i = indexOfFirst { it == ',' || it == ']' }
		if (get(i) == ',') i++
		return drop(i)
	}

	fun String.removeEndOfList(): String {
		var i = 1
		if (getOrNull(1) == ',') i++
		return drop(i)
	}


	fun isRightOrder(left: String, right: String): Boolean {
		when {
			left.isEndOfList() && !right.isEndOfList() -> return true
			!left.isEndOfList() && right.isEndOfList() -> return false
			left.isEndOfList() && right.isEndOfList()  -> return isRightOrder(left.removeEndOfList(), right.removeEndOfList())

			left.isAnInteger() && right.isAnInteger()  -> {
				val chL = left.firstElement().toInt()
				val chR = right.firstElement().toInt()
				return when {
					chL < chR -> true
					chL > chR -> false
					else      -> isRightOrder(left.dropFirstElement(), right.dropFirstElement())
				}
			}

			left.isAList() && right.isAList()          -> return isRightOrder(left.drop(1), right.drop(1))

			left.isAList() && right.isAnInteger()      -> return isRightOrder(left.drop(1), right.firstElement() + "]" + right.dropFirstElement())
			left.isAnInteger() && right.isAList()      -> return isRightOrder(left.firstElement() + "]" + left.dropFirstElement(), right.drop(1))

			else                                       -> TODO()
		}
	}

	fun part1(lines: List<String>): Int {
		var index = 0
		val x = lines.windowed(3, 3, partialWindows = true) {
			index++
			val (left, right) = it
			if (isRightOrder(left, right))
				index
			else
				0
		}
		return x.sum()
	}

	fun part2(lines: List<String>): Int {
		val l = lines.filter { it.isNotEmpty() }
			.plus("[[2]]")
			.plus("[[6]]")
			.sortedWith { a, b ->
				if (isRightOrder(a, b)) -1 else 1
			}
		return (l.indexOf("[[2]]") + 1) * (l.indexOf("[[6]]") + 1)
	}


	readLines(13, 140, ::part1, ::part2)
}