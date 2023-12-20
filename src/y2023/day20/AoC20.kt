package y2023.day20

import Day
import findLCM
import y2023.day20.AOC.Pulse.High
import y2023.day20.AOC.Pulse.Low

fun main() {
	AOC
}

private object AOC : Day<Int, Long>(11687500, 1, 925955316, 241528477694627) {

	data class Message(val target: String, val pulse: Pulse, val from: String)

	val modules = HashMap<String, Module>()
	val pulses = mutableListOf<Message>()
	var flipFlops = emptyList<FlipFlop>()

	const val OFF = false
	const val ON = true

	enum class Pulse { Low, High }

	var lowPulses = 0
	var highPulses = 0
	var output = Low

	fun send(m: Message) {
		pulses.add(m)
		when (m.pulse) {
			Low  -> lowPulses++
			High -> highPulses++
		}

//		println("${m.from} -${m.pulse}-> ${m.target}")
	}

	sealed class Module(val name: String, val connections: List<String>) {
		open fun process(msg: Message) {
			val pOut = makePulse(msg) ?: return
			connections.forEach { m ->
				send(Message(m, pOut, name))
			}
		}

		abstract fun makePulse(msg: Message): Pulse?
	}

	class Broadcaster(name: String, connections: List<String>) : Module(name, connections) {
		override fun makePulse(msg: Message) = msg.pulse
	}

	class FlipFlop(name: String, connections: List<String>) : Module(name, connections) {
		var status = OFF

		override fun makePulse(msg: Message): Pulse? {
			if (msg.pulse == High)
				return null
			status = !status
			return if (status == ON) High else Low
		}

		override fun toString() = "$name:${if (status == OFF) "OFF" else "ON"}"
	}

	class Conjunction(name: String, connections: List<String>) : Module(name, connections) {
		val memory = HashMap<String, Pulse>()
		fun init(src: String) = memory.set(src, Low)

		override fun makePulse(msg: Message): Pulse {
			memory[msg.from] = msg.pulse
			return if (memory.values.all { it == High }) Low else High
		}
	}

	fun pushButton(inspect: () -> Unit = {}) {
		send(Message("broadcaster", Low, "button"))
		while (pulses.isNotEmpty()) {
			val msg = pulses.removeFirst()
			val t = msg.target
			if (t == "rx")
				output = msg.pulse
			modules[t]?.process(msg)
			inspect()
		}
	}

	fun parse(lines: List<String>) {
		modules.clear()
		pulses.clear()
		lowPulses = 0
		highPulses = 0
		lines.forEach { line ->
			val (label, list) = line.split(" -> ")
			val dstList = list.split(", ")
			val type = label[0]
			val name = label.drop(1)
			when (type) {
				'b' -> modules[label] = Broadcaster(label, dstList)
				'%' -> modules[name] = FlipFlop(name, dstList)
				'&' -> modules[name] = Conjunction(name, dstList)
			}
		}

		flipFlops = modules.values.filterIsInstance<FlipFlop>()

		// Set up the Conjunctions
		val cList = modules.values.filterIsInstance<Conjunction>()
		cList.forEach { c ->
			modules.values.forEach { m ->
				if (c.name in m.connections)
					c.init(m.name)
			}
		}
	}

	init {
		part1Lines { lines ->
			parse(lines)
			repeat(1000) {
				pushButton()
			}
			lowPulses * highPulses
		}


		part2Lines(true) { lines ->
			parse(lines)
			var count = 0L
			val rxFeeder = modules.values.single { "rx" in it.connections }
			val generators = modules.values.filter { rxFeeder.name in it.connections }.map { it.name }.associateWith { 0L }.toMutableMap()
			while (true) {
				count++
				pushButton {
					generators.forEach { (mName, cycle) ->
						if (cycle == 0L)
							pulses
								.firstOrNull { it.from == mName && it.pulse == High }
								?.let { generators[mName] = count }
					}
				}
				if (generators.values.all { it > 0L })
					break
			}
			findLCM(generators.values.toList())
		}
	}
}