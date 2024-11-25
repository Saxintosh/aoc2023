import java.io.File
import java.math.BigInteger
import java.net.URL
import java.security.MessageDigest

fun readInputAsSequence() = findFile("input.txt").bufferedReader().lineSequence()
fun readInputAsList() = findFile("input.txt").readLines()
fun readInputAsText() = findFile("input.txt").readText()

fun readTestAsSequence() = findFile("test.txt").bufferedReader().lineSequence()
fun readTestAsList() = findFile("test.txt").readLines()
fun readTestAsText() = findFile("test.txt").readText()

private fun findFile(name: String): File {
	val e = Exception()
	val st = e.stackTrace[3]
	val partial = st.className.split(".").dropLast(1).joinToString("/")
	return File("src/main/kotlin/$partial/$name")
}

fun <T> readAll(test1: T, test2: T, part1: (String) -> T, part2: (String) -> T) {
	val testInput = readTestAsText()
	val input = readInputAsText()

	val res1 = part1(testInput)
	println("TestRes1 = $res1")
	check(res1 == test1)
	println("Res1 = ${part1(input)}")

	val res2 = part2(testInput)
	println("TestRes2 = $res2")
	check(res2 == test2)
	println("Res2 = ${part2(input)}")
}

fun <T> readLines(test1: T, test2: T, part1: (List<String>) -> T, part2: (List<String>) -> T) {
	val testInput = readTestAsList()
	val input = readInputAsList()

	val res1 = part1(testInput)
	println("TestRes1 = $res1")
	check(res1 == test1)
	println("Res1 = ${part1(input)}")

	val res2 = part2(testInput)
	println("TestRes2 = $res2")
	check(res2 == test2)
	println("Res2 = ${part2(input)}")
}

fun getSessionCookie(): String? = object {}.javaClass.getResource("session")?.readText()?.lines()?.firstOrNull()

fun download() {
	val file = findFile("input.txt")
	file.exists() && return

	val pathList = file.path.split("/")
	val year = pathList[3].takeLast(4)
	val day = pathList[4].takeLast(2).removePrefix("0")

	val url = URL("https://adventofcode.com/$year/day/$day/input")

	val connection = url.openConnection()
	connection.setRequestProperty("Cookie", "session=${getSessionCookie()}")
	connection.connect()
	val txt = connection.getInputStream().bufferedReader().readText()
	file.writeText(txt)

	println("+------------------+")
	println("+ File downloaded! +")
	println("+------------------+")
}

//    fun submitAnswer(year: Int, day: Int, level: Int, answer: Any?): List<String> {
//        println("Submitting answer for $year, day $day...")
//        val uri = "https://adventofcode.com/$year/day/$day/answer"
//
//        val payload = """{ "level": $level, "answer": \"$answer\" }"""
//
//        val url = URL(uri)
//        val result = mutableListOf<String>()
//        with(url.openConnection() as HttpURLConnection) {
//            requestMethod = "POST"
//			setRequestProperty("Cookie", "session=${getSessionCookie()}")
//            setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//            doOutput = true
//            outputStream.bufferedWriter().use { it.write(payload) }
//            inputStream.bufferedReader().useLines { result.addAll(it) }
//        }
//        return result
//    }
