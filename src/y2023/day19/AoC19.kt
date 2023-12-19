package y2023.day19

import Day

fun main() {
	AOC
}

private typealias CatRange = Map<Char, IntRange>

private object AOC : Day<Int, Long>(19114, 167409079868000, 456651, 131899818301477) {

	val CatRange.combinations get() = this.values.fold(1L) { acc, r -> acc * (r.last - r.first + 1) }

	sealed class Rule(val target: String) {
		open fun testIn(c: Category) = true
		open fun split(cRange: CatRange): Pair<CatRange, CatRange> = cRange to cRange
	}

	class Greater(val cat: String, val value: Int, target: String) : Rule(target) {
		override fun testIn(c: Category) = c.get(cat) > value
		override fun split(cRange: CatRange): Pair<CatRange, CatRange> {
			val r = cRange.getValue(cat[0])
			val rTrue = (value + 1..r.last)
			val rFalse = (r.first..value)
			return (cRange + (cat[0] to rTrue)) to (cRange + (cat[0] to rFalse))
		}
	}

	class Lesser(val cat: String, val value: Int, target: String) : Rule(target) {
		override fun testIn(c: Category) = c.get(cat) < value
		override fun split(cRange: CatRange): Pair<CatRange, CatRange> {
			val r = cRange.getValue(cat[0])
			val rTrue = (r.first..<value)
			val rFalse = (value..r.last)
			return (cRange + (cat[0] to rTrue)) to (cRange + (cat[0] to rFalse))
		}
	}

	class Otherwise(target: String) : Rule(target)

	data class Category(val x: Int, val m: Int, val a: Int, val s: Int) {
		val rating = x + m + a + s
		fun get(c: String) = when (c) {
			"x"  -> x
			"m"  -> m
			"a"  -> a
			"s"  -> s
			else -> TODO()
		}
	}

	fun String.toRule(): Rule {
		if (':' !in this)
			return Otherwise(this)
		val cat = this.take(1)
		val test = this[1]
		val (value, target) = this.drop(2).split(':')
		return when (test) {
			'>'  -> Greater(cat, value.toInt(), target)
			else -> Lesser(cat, value.toInt(), target)
		}
	}

	fun String.parse(): Pair<Map<String, List<Rule>>, List<Category>> {
		val (a, b) = this.split("\n\n")
		val first = a.lines().associate { line ->
			val (label, tests) = line.dropLast(1).split('{')
			val tList = tests.split(',').map { it.toRule() }
			label to tList
		}

		val second = b.lines()
			.map { line ->
				val l = line.drop(1).dropLast(1).split(",").map { it.drop(2).toInt() }
				Category(l[0], l[1], l[2], l[3])
			}

		return first to second
	}

	tailrec fun Map<String, List<Rule>>.accept(t: String, c: Category): Boolean {
		val rList = this.getValue(t)
		for (r in rList) {
			if (r.testIn(c)) {
				return when (r.target) {
					"A"  -> true
					"R"  -> false
					else -> accept(r.target, c)
				}
			}
		}
		TODO()
	}

	init {
		part1Text { txt ->
			val (workflows, l) = txt.parse()
			l
				.filter { workflows.accept("in", it) }
				.sumOf { it.rating }
		}


		part2Text { txt ->
			val workflows = txt.parse().first

			fun combinations(cRange: CatRange, label: String = "in"): Long {
				when (label) {
					"R" -> return 0
					"A" -> return cRange.combinations
				}

				var localSum = 0L
				val rList = workflows.getValue(label).toMutableList()
				var crCurrent = cRange
				while (rList.isNotEmpty()) {
					val r = rList.removeFirst()
					val (crTrue, crFalse) = r.split(crCurrent)
					crCurrent = crFalse
					localSum += combinations(crTrue, r.target)
				}
				return localSum
			}

			val c = "xmas".associate { it to (1..4000) }
			combinations(c)
		}
	}
}