package y2022.day18

import download
import readLines

private val incList = listOf(
	P3(-1, 0, 0), P3(1, 0, 0),
	P3(0, -1, 0), P3(0, 1, 0),
	P3(0, 0, -1), P3(0, 0, 1),
)

private data class P3(val x: Int, val y: Int, val z: Int) {
	fun getAdj(): List<P3> = incList.map { P3(x + it.x, y + it.y, z + it.z) }
	fun faces() = listOf(
		Face(this, 'X'), Face(P3(x - 1, y, z), 'X'),
		Face(this, 'Y'), Face(P3(x, y - 1, z), 'Y'),
		Face(this, 'Z'), Face(P3(x, y, z - 1), 'Z'),
	)
}

private data class Face(val p: P3, val f: Char)

private class Bubble(p: P3? = null) {
	val s = mutableSetOf<P3>()

	init {
		if (p != null) s.add(p)
	}

	fun add(p: P3) = s.add(p)
	operator fun contains(p: P3) = p in s
	fun addAll(b: Bubble) = s.addAll(b.s)

}

private class SetOfBubble {
	val ss = mutableSetOf<Bubble>()

	fun put(p: P3, pp: List<P3>) {
		val sets = ss.filter { b -> pp.any { it in b } }
		when (sets.size) {
			0    -> ss.add(Bubble(p))
			1    -> sets.first().add(p)
			else -> {
				// join
				ss.removeAll(sets.toSet())
				val newSet = Bubble()
				sets.forEach { newSet.addAll(it) }
				newSet.add(p)
				ss.add(newSet)
			}
		}
	}

	fun processAir(pList: List<P3>) {
		val minX = pList.minBy { it.x }.x - 1
		val minY = pList.minBy { it.y }.y - 1
		val minZ = pList.minBy { it.z }.z - 1
		val maxX = pList.maxBy { it.x }.x + 1
		val maxY = pList.maxBy { it.y }.y + 1
		val maxZ = pList.maxBy { it.z }.z + 1
		val rx = minX..maxX
		val ry = minY..maxY
		val rz = minZ..maxZ

		for (x in rx)
			for (y in ry)
				for (z in rz) {
					val p = P3(x, y, z)
					val pp = p.getAdj()
						.filter { it !in pList && it.x in rx && it.y in ry && it.z in rz }
					if (p !in pList)
						put(p, pp)
				}

		// remove open air
		ss.removeIf { P3(minX, minY, minZ) in it }
	}
}

private fun parse(list: List<String>) = list
	.map { it.split(",") }
	.map { P3(it[0].toInt(), it[1].toInt(), it[2].toInt()) }

private fun getAllFaces(pList: List<P3>): MutableSet<Face> {
	val faces = mutableSetOf<Face>()
	pList.forEach { p ->
		p.faces().forEach { face ->
			if (face in faces)
				faces.remove(face)
			else
				faces.add(face)
		}
	}
	return faces
}

fun main() {

	download()

	fun part1(lines: List<String>): Int {
		val pList = parse(lines)

		val faces = getAllFaces(pList)

		return faces.size
	}

	fun part2(lines: List<String>): Int {
		val pList = parse(lines)

		val faces = getAllFaces(pList)

		val airs = SetOfBubble()
		airs.processAir(pList)
		airs.ss.flatMap { it.s.toList() }.forEach { p ->
			p.faces().forEach { face ->
				faces.remove(face)
			}
		}
		return faces.size
	}


	readLines(64, 58, ::part1, ::part2)
}