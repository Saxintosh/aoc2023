class Graph<N, EDGE>(val defaultEdge: EDGE, val oriented: Boolean = true) {

	inner class Node(var value: N) {
		private val edgeMap = mutableMapOf<Node, Edge>()
		val connected: Set<Node> get() = edgeMap.keys

		fun isConnectedTo(n2: Node) = n2 in edgeMap
		fun getEdgeTo(n2: Node) = edgeMap[n2]
		fun connect(n2: Node, e: Edge) = edgeMap.set(n2, e)
		override fun toString() = value.toString()
	}

	inner class Edge(var value: EDGE)

	private val nodeList = mutableListOf<Node>()
	val nodes: List<Node> = nodeList

	fun addVertex(e: N) = Node(e).also { nodeList.add(it) }
	fun connect(n1: Node, n2: Node, eVal: EDGE = defaultEdge) {
		n1.getEdgeTo(n2)?.let {
			it.value = eVal
			return
		}
		val e = Edge(eVal)
		n1.connect(n2, e)
		if (!oriented)
			n2.connect(n1, e)
	}
}