package y2021.day01

import y2021.readInputAsSequence

fun main() {
	println(
		readInputAsSequence()
			.map { it.toInt() }
			.zipWithNext()
			.count { (first, second) -> second > first }
	)

	println(
		readInputAsSequence()
			.map { it.toInt() }
			.windowed(3)
			.map { it.sum() }
			.zipWithNext()
			.count { (first, second) -> second > first }
	)


	// A + B + C < B + C + D
	// A + (B + C) < (B + C) + D
	// A < D
	// Quindi potrei fare un solo windowed(4)
	val res3 = readInputAsSequence()
		.map { it.toInt() }
		.windowed(4)
		.count { it[0] < it[3] }

	println(res3)
}