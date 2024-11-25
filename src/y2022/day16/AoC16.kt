package y2022.day16

import com.google.common.collect.Sets
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph
import download
import readLines
import kotlin.math.min

private class Status(val closed: Set<Valve>, val last: Valve) {
	var pression = 0
	var flowRates = 0
	var minutes = 0

	override fun toString() = "${last.name} p:$pression f:${flowRates} m:${minutes}"

	private fun open(v: Valve, d: Int): Status {
		val s = Status(closed.minus(v), v)
		val minutesLeft = 30 - minutes
		if (d < minutesLeft) {
			// move
			s.minutes = minutes + d
			s.pression = pression + flowRates * d
			// open
			s.minutes++
			s.pression += flowRates
			s.flowRates = flowRates + v.flowRate
		} else {
			s.pression = pression + flowRates * (30 - minutes)
			s.flowRates = flowRates
			s.minutes = 30
		}
		return s
	}

	fun oneStep(): List<Status> {
		if (minutes == 30) return listOf(this)

		val minutesLeft = 30 - minutes
		if (closed.isEmpty()) {
			pression += flowRates * minutesLeft
			minutes += minutesLeft
			return listOf(this)
		}

		val l = mutableListOf<Status>()
		for (v in closed) {
			val d = dis[last, v]
			l.add(open(v, d))
		}
		return l
	}

}

private data class Valve(val name: String, val flowRate: Int)

private class Dis {
	val dis = HashMap<Valve, HashMap<Valve, Int>>()

	fun prepare(v: Valve) {
		dis[v] = HashMap()
	}

	operator fun get(v1: Valve, v2: Valve) = dis[v1]!![v2] ?: 1000
	operator fun set(v1: Valve, v2: Valve, value: Int) {
		dis[v1]!![v2] = value
		dis[v2]!![v1] = value
	}

}

private var dis = Dis()


private class Vulcano(lines: List<String>) {
	val graph: MutableGraph<Valve> = GraphBuilder.undirected().build()

	init {
		lines.map {
			val s = it.replace("=", " ").replace(",", "").split(" ")
			val name = s[1]
			val flowRate = s[5].dropLast(1).toInt()
			val adjValves = s.drop(10)
			val valve = Valve(name, flowRate)
			dis.prepare(valve)
			graph.addNode(valve)
			valve to adjValves
		}.forEach { (vFrom, adjValves) ->
			for (vStr in adjValves) {
				val vTo = graph.nodes().first { it.name == vStr }
				graph.putEdge(vFrom, vTo)
				dis[vFrom, vTo] = 1
			}
		}

		// compute all distances (floyd-warshall)
		Sets.cartesianProduct(graph.nodes(), graph.nodes(), graph.nodes()).forEach {
			val (k, i, j) = it.toList()
			dis[i, j] = min(dis[i, j], dis[i, k] + dis[k, j])
		}

		// remove valves with 0 flow rate
		graph.nodes()
			.filter { v -> v.name != "AA" && v.flowRate == 0 }
			.forEach { middleValve ->
				val couples = Sets.combinations(graph.adjacentNodes(middleValve), 2)
				couples.forEach {
					val (v1, v2) = it.toList()
					val d = dis[v1, middleValve] + dis[middleValve, v2]
					graph.putEdge(v1, v2)
					dis[v1, v2] = d
				}
				graph.removeNode(middleValve)
			}
	}

	fun printGraph() {
		val visited = mutableSetOf<Valve>()
		graph.nodes().forEach { v1 ->
			graph.adjacentNodes(v1).forEach { v2 ->
				if (v2 !in visited)
					println("${v1.name}-${v2.name} -> ${dis[v1, v2]}")
			}
			visited.add(v1)
		}
	}

	fun process(): Int {
		val aa = graph.nodes().first { it.name == "AA" }
		val closed = graph.nodes().minus(aa)

		var statusList = listOf(Status(closed, aa))

		while (statusList.any { it.minutes < 30 }) {
			statusList = statusList.flatMap { it.oneStep() }
		}

		return statusList.maxOf { it.pression }
	}
}

fun main() {

	download()

	fun part1(lines: List<String>): Int {
		dis = Dis()
		val v = Vulcano(lines)
		return v.process()
	}

	fun part2(lines: List<String>): Int {
		return 1
	}


	readLines(1651, 0, ::part1, ::part2)
}