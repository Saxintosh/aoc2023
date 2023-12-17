import java.io.File
import java.io.FileNotFoundException
import kotlin.time.measureTimedValue

@DslMarker
annotation class AdventOfCode

@AdventOfCode
open class Day<T1, T2>(
	testRes1: T1,
	testRes2: T2,
	realRes1: T1? = null,
	realRes2: T2? = null
) {
	private val srcPath: String

	init {
		val e = Exception()
		val s = e.stackTrace.first { it.className.startsWith("y") }
		val path = s.className.split(".").dropLast(1).joinToString("/")
		srcPath = "./src/$path/"
	}

	private val fInput = File(srcPath + "input.txt")
		.takeIf { it.exists() } ?: throw FileNotFoundException()
	private val fTest1 = File(srcPath + "test.txt")
		.takeIf { it.exists() } ?: throw FileNotFoundException()
	private var fTest2 = File(srcPath + "test2.txt")
		.takeIf { it.exists() } ?: File(srcPath + "test.txt")
		.takeIf { it.exists() } ?: throw FileNotFoundException()

	private val fTests = listOf(fTest1, fTest2)
	private val testResults = listOf(testRes1, testRes2)
	private val properResults = listOf(realRes1, realRes2)

	private fun <SRC, T> part(part: Int, reader: File.() -> SRC, block: (SRC) -> T) {
		var lines = fTests[part].reader()
		var res = measureTimedValue { block(lines) }
		println("Test Part ${part + 1} = ${res.value}")
		check(res.value == testResults[part])

		lines = fInput.reader()
		res = measureTimedValue { block(lines) }
		println("     Part ${part + 1} = ${res.value} in ${res.duration}")
		properResults[part]?.let { check(it == res.value) }
	}

	@AdventOfCode
	fun part1Lines(block: (List<String>) -> T1) = part(0, File::readLines, block)
	@AdventOfCode
	fun part2Lines(block: (List<String>) -> T2) = part(1, File::readLines, block)

	@AdventOfCode
	fun part1Text(block: (String) -> T1) = part(0, File::readText, block)
	@AdventOfCode
	fun part2Text(block: (String) -> T2) = part(1, File::readText, block)

}
