package y2015.day07

sealed class Gate {
	abstract fun isReady(mem: Memory): Boolean
	abstract fun process(mem: Memory)
}

class GSet(val src: Source, val dest: String): Gate() {
	override fun isReady(mem: Memory) = src.isAValue(mem)
	override fun process(mem: Memory) = src.value(mem).let { mem[dest] = it }
}

class GNot(val src: Source, val dest: String): Gate() {
	override fun isReady(mem: Memory) = src.isAValue(mem)
	override fun process(mem: Memory) = src.value(mem).let { mem[dest] = it.inv() }
}

class GAnd(val s1: Source, val s2: Source, val dest: String): Gate() {
	override fun isReady(mem: Memory) = s1.isAValue(mem) && s2.isAValue(mem)
	override fun process(mem: Memory) {
		mem[dest] = s1.value(mem) and s2.value(mem)
	}
}

class GOr(val s1: Source, val s2: Source, val dest: String): Gate() {
	override fun isReady(mem: Memory) = s1.isAValue(mem) && s2.isAValue(mem)
	override fun process(mem: Memory) {
		mem[dest] = s1.value(mem) or s2.value(mem)
	}

}

class GLShift(val s1: Source, val sh: Int, val dest: String): Gate() {
	override fun isReady(mem: Memory) = s1.isAValue(mem)
	override fun process(mem: Memory) {
		mem[dest] = s1.value(mem) shl sh
	}
}

class GRShift(val s1: Source, val sh: Int, val dest: String): Gate() {
	override fun isReady(mem: Memory) = s1.isAValue(mem)
	override fun process(mem: Memory) {
		mem[dest] = s1.value(mem) shr sh
	}
}
