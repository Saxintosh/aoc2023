package y2021.day07

import y2021.readInputAsList
import kotlin.math.abs

fun main() {

	// Sommatoria dei primi termini della serie aritmetica 1+2+3+4+....
	fun cost(term: Int) = (term * (term + 1)) / 2

	val list = readInputAsList()[0]
		.split(",")
		.map { it.toInt() }

	val min = list.minOrNull()!!
	val max = list.maxOrNull()!!

	val sumList = (min..max).map { pos ->
		list.sumOf { abs(it - pos) }
	}

	println(sumList.minOrNull())

	val sumList2 = (min..max).map { pos ->
		list.sumOf { cost(abs(it - pos)) }
	}


	println(sumList2.minOrNull())
}