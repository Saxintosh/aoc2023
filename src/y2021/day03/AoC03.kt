package y2021.day03

import y2021.readInputAsList
import y2021.readInputAsSequence

fun main() {
	var maxIndex = 0
	val onesCount = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

	readInputAsSequence().forEach { line ->
		line.forEachIndexed { index, ch -> if (ch == '1') onesCount[index]++ }
		maxIndex++
	}

	val half = maxIndex.div(2)

	val gammaRate = onesCount.map { if (it > half) 1 else 0 }.fold("") { acc, i -> acc + i.toString() }
	val epsilonRate = onesCount.map { if (it <= half) 1 else 0 }.fold("") { acc, i -> acc + i.toString() }

	print("power consumption: ")
	println(gammaRate.toInt(2) * epsilonRate.toInt(2))


	// part 2

	val mainList = readInputAsList()
	val columns = mainList[0].indices

	fun List<String>.mostCommonBit(col: Int) = if (count { it[col] == '1' } >= (size / 2.0)) '1' else '0'
	fun List<String>.lessCommonBit(col: Int) = if (count { it[col] == '1' } < (size / 2.0)) '1' else '0'

	var oxygenList = mainList

	for (col in columns) {
		val mcb = oxygenList.mostCommonBit(col)
		oxygenList = oxygenList.filter { it[col] == mcb }
		if (oxygenList.size == 1) break
	}

	println(oxygenList.single())
	val oxygen = oxygenList.single().toInt(2)
	println("oxygen: $oxygen")

	var co2List = mainList

	for (col in columns) {
		val lcb = co2List.lessCommonBit(col)
		co2List = co2List.filter { it[col] == lcb }
		if (co2List.size == 1) break
	}

	println(co2List.single())
	val co2 = co2List.single().toInt(2)
	println("co2: $co2")

	println("life support rating: ${oxygen * co2}")

}