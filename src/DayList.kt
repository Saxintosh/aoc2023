import java.io.File
import java.io.FileNotFoundException
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

abstract class DayList<T1, T2>(
	private val testPart1: T1,
	private val testPart2: T2,
	private val resultPart1: T1? = null,
	private val resultPart2: T2? = null
) {
	abstract fun part1(lines: List<String>): T1
	abstract fun part2(lines: List<String>): T2

	protected open fun doPart1(f: File): TimedValue<T1> {
		val lines = f.readLines()
		return measureTimedValue {
			part1(lines)
		}
	}

	protected open fun doPart2(f: File): TimedValue<T2> {
		val lines = f.readLines()
		return measureTimedValue {
			part2(lines)
		}
	}

	fun run() {
		val srcPath = "./src/${this.javaClass.`package`.name}/"

		val ft1 = File(srcPath + "test.txt").takeIf { it.exists() } ?: throw FileNotFoundException()
		doPart1(ft1).let { (res, _) ->
			println("Test Part 1 = $res")
			check(res == testPart1)
		}

		val i = File(srcPath + "input.txt").takeIf { it.exists() } ?: throw FileNotFoundException()
		doPart1(i).let { (res, d) ->
			println("Part 1 = $res in $d")
			resultPart1?.let { check(it == res) }
		}

		val ft2 = File(srcPath + "test2.txt").takeIf { it.exists() } ?: ft1
		doPart2(ft2).let { (res, _) ->
			println("Test Part 2 = $res")
			check(res == testPart2)
		}

		doPart2(i).let { (res, d) ->
			println("Part 2 = $res in $d")
			resultPart2?.let { check(it == res) }
		}
	}
}
