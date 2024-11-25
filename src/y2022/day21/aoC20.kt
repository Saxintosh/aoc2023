package y2022.day21

import download
import readLines
import java.lang.Exception

fun main() {

	download()

	fun evaluate(vars: HashMap<String, Any>, a: String): Long? {
		when (val v = vars[a]) {
			is Long   -> return v
			is String -> {
				val (x, op, y) = v.split(" ")
				val xx = evaluate(vars, x)
				val yy = evaluate(vars, y)
				if (xx == null || yy == null)
					return null
				val result = when (op) {
					"+"  -> xx + yy
					"-"  -> xx - yy
					"*"  -> xx * yy
					"/"  -> xx / yy
					else -> TODO()
				}
				vars[a] = result
				return result
			}

			else      -> return null
		}
	}

	tailrec fun findHuman(vars: HashMap<String, Any>, a: String, h: Long): Long {
		if (a == "humn")
			return h

		val v = vars[a]
		val (x, op, y) = (v as String).split(" ")
		val xx = evaluate(vars, x)
		val yy = evaluate(vars, y)

		return when (op) {
			"+"  -> {
				// xx + "y" = h  -> "y" =
				// "x" + yy = h  -> "x" =
				if (xx is Long && yy == null)
					findHuman(vars, y, h - xx)
				else if (yy is Long && xx == null)
					findHuman(vars, x, h - yy)
				else
					throw Exception()
			}

			"-"  -> {
				// xx - "y" = h  -> "y" =
				// "x" - yy = h  -> "x" =
				if (xx is Long && yy == null)
					findHuman(vars, y, xx - h)
				else if (yy is Long && xx == null)
					findHuman(vars, x, h + yy)
				else
					throw Exception()
			}

			"*"  -> {
				// xx * "y" = h  -> "y" =
				// "x" * yy = h  -> "x" =
				if (xx is Long && yy == null)
					findHuman(vars, y, h / xx)
				else if (yy is Long && xx == null)
					findHuman(vars, x, h / yy)
				else
					throw Exception()
			}

			"/"  -> {
				// xx / "y" = h  -> "y" =
				// "x" / yy = h  -> "x" =
				if (xx is Long && yy == null)
					findHuman(vars, y, xx / h)
				else if (yy is Long && xx == null)
					findHuman(vars, x, h * yy)
				else
					throw Exception()
			}

			else -> throw Exception()
		}
	}

	fun part1(lines: List<String>): Long {
		val vars = HashMap<String, Any>()
		lines.map {
			val (a, b) = it.split(": ")
			val bb = b.toLongOrNull()
			vars[a] = bb ?: b
		}
		return evaluate(vars, "root")!!
	}

	fun part2(lines: List<String>): Long {
		val vars = HashMap<String, Any>()
		lines.map {
			val (a, b) = it.split(": ")
			val bb = b.toLongOrNull()
			vars[a] = bb ?: b
		}

		vars.remove("humn")
		val (lf, rh) = (vars["root"] as String).split(" + ")
		var k = ""
		var v = 0L

		val r1 = evaluate(vars, lf)
		if (r1 != null)
			v = r1
		else
			k = lf

		val r2 = evaluate(vars, rh)
		if (r2 != null)
			v = r2
		else
			k = rh

		return findHuman(vars, k, v)
	}


	readLines(152L, 301L, ::part1, ::part2)
}