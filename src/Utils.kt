@file:Suppress("unused")

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
	.toString(16)
	.padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun myRange(a: Int, b: Int) = when (a < b) {
	true -> a..b
	else -> b..a
}

fun myRangeUntil(a: Int, b: Int) = when (a < b) {
	true -> a until b
	else -> b until a
}

tailrec fun gcd(a: Long, b: Long): Long {
	return if (b == 0L) a else gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
	return if (a == 0L || b == 0L) 0L else abs(a * b) / gcd(a, b)
}

fun findLCM(numbers: List<Long>): Long {
	if (numbers.isEmpty()) {
		throw IllegalArgumentException("List cannot be empty")
	}

	var result = numbers[0]

	for (i in 1 until numbers.size) {
		result = lcm(result, numbers[i])
	}

	return result
}
