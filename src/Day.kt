import java.io.File
import java.io.FileNotFoundException
import kotlin.system.exitProcess
import kotlin.time.measureTimedValue

@DslMarker
annotation class AdventOfCode

@AdventOfCode
open class Day<T1, T2>(
	testRes1: T1,
	testRes2: T2,
	val realRes1: T1? = null,
	val realRes2: T2? = null
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

	private fun <SRC, T> part(part: Int, skipTest: Boolean, reader: File.() -> SRC, block: (SRC) -> T) {
		if (!skipTest) {
			val lines = fTests[part].reader()
			val res = measureTimedValue { block(lines) }
			println("Test Part ${part + 1} = ${res.value}")
			if (res.value != testResults[part]) {
				println("              ERROR: ${testResults[part]} expected!")
				exitProcess(1)
			}
		}
		val lines2 = fInput.reader()
		val res2 = measureTimedValue { block(lines2) }
		println("     Part ${part + 1} = ${res2.value} in ${res2.duration}")
		properResults[part]?.let {
			if(it != res2.value) {
				println("              ERROR: ${it} expected!")
				exitProcess(1)
			}
		}
	}

	@AdventOfCode
	fun part1Lines(skipTest: Boolean = false, block: (List<String>) -> T1) = part(0, skipTest, File::readLines, block)

	@AdventOfCode
	fun part2Lines(skipTest: Boolean = false, block: (List<String>) -> T2) = part(1, skipTest, File::readLines, block)

	@AdventOfCode
	fun part1Text(skipTest: Boolean = false, block: (String) -> T1) = part(0, skipTest, File::readText, block)

	@AdventOfCode
	fun part2Text(skipTest: Boolean = false, block: (String) -> T2) = part(1, skipTest, File::readText, block)

}
