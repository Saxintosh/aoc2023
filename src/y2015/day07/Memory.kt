package y2015.day07


class Memory: HashMap<String, Int>() {

	fun process(queue: List<Gate>): List<Gate> {
		val newQueue = mutableListOf<Gate>()
		queue.forEach { gate ->
			if (gate.isReady(this))
				gate.process(this)
			else
				newQueue.addLast(gate)
		}

		return newQueue
	}
}
