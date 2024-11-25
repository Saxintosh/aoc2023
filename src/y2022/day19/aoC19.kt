package y2022.day19

import download
import readLines

private data class Blueprint(
	val id: Int,
	val oreCost: Int, // Ore
	val clayCost: Int, // Ore
	val obsidianCost: Pair<Int, Int>, // Ore, Clay
	val geodeCost: Pair<Int, Int>, // Ore, Obsidian
) {
	val maxOre = listOf(oreCost, clayCost, obsidianCost.first, geodeCost.first).max()
	val maxClay = obsidianCost.second
	val maxObsidian = geodeCost.second
}

private data class Status(
	val ore: Int = 0,
	val clay: Int = 0,
	val obsidian: Int = 0,
	val geode: Int = 0,

	val rOre: Int = 1,
	val rClay: Int = 0,
	val rObsidian: Int = 0,
	val rGeode: Int = 0,
) {
	fun makeNothing(b: Blueprint): Status? {
		if (ore == b.maxOre && clay == b.maxClay && obsidian == b.maxObsidian)
			return null
		return copy(ore = ore + rOre, clay = clay + rClay, obsidian = obsidian + rObsidian, geode = geode + rGeode)
	}

	fun makeOreBot(b: Blueprint) = if (ore >= b.oreCost)
		copy(rOre = rOre + 1, ore = ore - b.oreCost + rOre, clay = clay + rClay, obsidian = obsidian + rObsidian, geode = geode + rGeode)
	else
		null


	fun makeClayBot(b: Blueprint) = if (ore >= b.clayCost)
		copy(rClay = rClay + 1, ore = ore - b.clayCost + rOre, clay = clay + rClay, obsidian = obsidian + rObsidian, geode = geode + rGeode)
	else
		null

	fun makeObsidianBot(b: Blueprint) = if (ore >= b.obsidianCost.first && clay >= b.obsidianCost.second)
		copy(rObsidian = rObsidian + 1, ore = ore - b.obsidianCost.first + rOre, clay = clay - b.obsidianCost.second + rClay, obsidian = obsidian + rObsidian, geode = geode + rGeode)
	else
		null

	fun makeGeodeBot(b: Blueprint) = if (ore >= b.geodeCost.first && obsidian >= b.geodeCost.second)
		copy(rGeode = rGeode + 1, ore = ore - b.geodeCost.first + rOre, clay = clay + rClay, obsidian = obsidian - b.geodeCost.second + rObsidian, geode = geode + rGeode)
	else
		null
}


private fun bfs(b: Blueprint, timeLeft: Int, cache: HashMap<Any, Int>, status: Status?): Int {
	if (status == null)
		return 0

	if (timeLeft == 0)
		return status.geode

	val key = timeLeft to status

	if (key in cache)
		return cache[key]!!

	val sg = status.makeGeodeBot(b)
	val max = if (sg != null)
		bfs(b, timeLeft - 1, cache, sg)
	else {
		mutableListOf(
			bfs(b, timeLeft - 1, cache, status.makeObsidianBot(b)),
			bfs(b, timeLeft - 1, cache, status.makeClayBot(b)),
			bfs(b, timeLeft - 1, cache, status.makeOreBot(b)),
			bfs(b, timeLeft - 1, cache, status.makeNothing(b)),
		).max()
	}

	cache[key] = max
	return max
}

private fun parse(lines: List<String>) = lines.map {
	val s = it.split(" ")
	Blueprint(
		s[1].dropLast(1).toInt(),
		s[6].toInt(), // Ore
		s[12].toInt(), // Ore
		Pair(s[18].toInt(), s[21].toInt()), // Ore, Clay
		Pair(s[27].toInt(), s[30].toInt()) // Ore, Obsidian
	)
}


fun main() {

	download()

	fun part1(lines: List<String>): Int {
		val blueprint = parse(lines)
		return blueprint.sumOf {
			val max = bfs(it, 24, HashMap(), Status())
			println("${it.id}: $max")
			it.id * max
		}
	}

	fun part2(lines: List<String>): Int {
		return 1
	}


	readLines(33, 58, ::part1, ::part2)
}