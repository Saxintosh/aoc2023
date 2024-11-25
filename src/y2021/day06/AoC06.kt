package y2021.day06

import y2021.readInputAsList

class Simulator {
	// da 0 a 8
	private val arr = LongArray(9) { 0 }

	private var index0 = 0

	fun addInitialState(timer: Int) {
		arr[timer] = arr[timer] + 1
	}

	fun advance() {
		val index6 = (index0 + 7) % 9

		arr[index6] = arr[index6] + arr[index0]

		index0 = (index0 + 1) % 9
	}

	fun sum() = arr.sum()

	override fun toString() = arr.joinToString { it.toString() }
}

fun main() {

	val simulator = Simulator()

	readInputAsList()[0]
		.split(",")
		.map { it.toInt() }
		.forEach { simulator.addInitialState(it) }

//	repeat(80) {
//		simulator.advance()
//	}
	repeat(256) {
		simulator.advance()
	}
	println(simulator.sum())

}
