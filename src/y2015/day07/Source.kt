package y2015.day07


sealed class Source {
	abstract fun isAValue(mem: Memory): Boolean
	abstract fun value(mem: Memory): Int

	companion object {
		fun from(s: String) = when (val res = s.toIntOrNull()) {
			is Int -> Constant(res)
			else   -> Wire(s)
		}
	}
}

class Constant(val value: Int): Source() {
	override fun isAValue(mem: Memory) = true
	override fun value(mem: Memory) = value
}

class Wire(val name: String): Source() {
	override fun isAValue(mem: Memory) = mem.contains(name)
	override fun value(mem: Memory) = mem[name]!!
}
