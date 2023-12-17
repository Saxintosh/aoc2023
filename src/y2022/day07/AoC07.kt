package y2022.day07

import Day


interface AnElement {
	val name: String
	var size: Int
}

class AFile(
	override val name: String,
	override var size: Int,
) : AnElement

class ADir(
	override val name: String,
) : AnElement {
	override var size: Int = 0
	private val content = HashMap<String, AnElement>()
	var parent: ADir? = null
		private set

	private val localDirs get() = content.values.filterIsInstance(ADir::class.java)

	fun getAllDirectories(): List<ADir> = localDirs.flatMap { it.getAllDirectories() } + localDirs

	fun addFile(name: String, size: Int) {
		content[name] = AFile(name, size)
		addChildSize(size)
	}

	private fun addChildSize(size: Int) {
		this.size += size
		parent?.addChildSize(size)
	}

	fun createDir(name: String): ADir = ADir(name).also {
		it.parent = this
		content[name] = it
	}

	fun getDir(name: String) = content[name] as ADir
}

class Disk {
	val root = ADir("/")
	private var cwd = root

	fun cd(name: String) {
		cwd = when (name) {
			"/"  -> root
			".." -> cwd.parent!!
			else -> cwd.getDir(name)
		}
	}

	fun processCmd(cmd: String) {
		val parts = cmd.split(" ")
		val (p1, p2) = parts
		when (p1) {
			"$"   -> if (p2 == "cd") cd(parts[2])
			"dir" -> createDir(p2)
			else  -> addFile(p2, p1.toInt())
		}
	}

	fun createDir(name: String) = cwd.createDir(name)
	fun addFile(name: String, size: Int) = cwd.addFile(name, size)
	fun findSmallDir(limit: Int) = root.getAllDirectories().filter { it.size <= limit }.sumOf { it.size }

	companion object {
		fun from(lines: List<String>) = Disk().apply {
			lines.forEach(::processCmd)
		}
	}
}

fun main() {
	AOC
}

private object AOC : Day<Int, Int>(95437, 24933642, 1423358, 545729) {

	init {
		part1Lines { lines ->
			val disk = Disk.from(lines)
			disk.findSmallDir(100000)
		}

		part2Lines { lines ->
			val disk = Disk.from(lines)
			val free = 70000000 - disk.root.size
			val needed = 30000000 - free
			val l = disk.root.getAllDirectories().sortedBy { it.size }.first { it.size >= needed }
			l.size
		}
	}
}
