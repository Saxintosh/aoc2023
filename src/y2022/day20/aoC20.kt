package y2022.day20

import download
import readLines

data class Node(val value: Long) {
	var prev = this
	var next = this

	fun addAfter(n1: Node) {
		this.next = n1.next
		this.prev = n1
		n1.next.prev = this
		n1.next = this
	}

	fun remove() {
		this.prev.next = this.next
		this.next.prev = this.prev
	}

	fun next(times: Int): Node {
		var n = this
		repeat(times) { n = n.next }
		return n
	}

	fun prev(times: Int): Node {
		var n = this
		repeat(times) { n = n.prev }
		return n
	}
}

class CircularList : Iterable<Node> {
	lateinit var firstNode: Node
	private val naturalOrder = mutableListOf<Node>()

	fun addNewNode(value: Long) {
		val n = Node(value)
		if (naturalOrder.size == 0)
			firstNode = n
		else
			n.addAfter(firstNode.prev)
		naturalOrder.add(n)
	}


	fun findNode(value: Long) = asSequence().first { it.value == value }

	private fun move(n: Node) {
		val value = n.value
		if (value == 0L)
			return

		val n1 = n.prev
		n.remove()

		val n2 = if (value > 0)
			n1.next((value % (naturalOrder.size - 1)).toInt())
		else
			n1.prev(((-value) % (naturalOrder.size - 1)).toInt())

		n.addAfter(n2)
	}

	fun move() {
		naturalOrder.forEach { move(it) }
	}

	override fun iterator() = object : Iterator<Node> {
		var isDone = false
		var n = firstNode
		override fun hasNext() = !isDone
		override fun next(): Node {
			n = n.next
			if (n == firstNode)
				isDone = true
			return n.prev
		}
	}

	override fun toString() = asSequence().joinToString { it.value.toString() }
}


fun main() {

	download()

	fun part1(lines: List<String>): Long {
		val l = lines.map { it.toLong() }
		val ll = CircularList()
		l.forEach { ll.addNewNode(it) }

		ll.move()

		val z = ll.findNode(0)

		val z1 = z.next(1000)
		val z2 = z1.next(1000)
		val z3 = z2.next(1000)
		return listOf(
			z1.value,
			z2.value,
			z3.value,
		).sum()
	}

	fun part2(lines: List<String>): Long {
		val l = lines.map { it.toLong() * 811589153L }
		val ll = CircularList()
		l.forEach { ll.addNewNode(it) }

		repeat(10) { ll.move() }

		val z = ll.findNode(0)

		val z1 = z.next(1000)
		val z2 = z1.next(1000)
		val z3 = z2.next(1000)
		return listOf(
			z1.value,
			z2.value,
			z3.value,
		).sum()
	}


	readLines(3L, 1623178306L, ::part1, ::part2)
}