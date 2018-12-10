import java.util.*

object Day7 {
    //
    private fun run(input: List<String>) {

        val depPairs: List<Pair<Char, Char>> = input.map {
            val (dependency, step) = """Step (.) must be finished before step (.) can begin.""".toRegex().find(it)!!.destructured
            (dependency.toCharArray()[0] to step.toCharArray()[0])
        }

        println(depPairs)

        val stepToNextSteps: Map<Char, List<Char>> = depPairs.groupBy({ it.first }, { it.second })
        var dependencies: MutableMap<Char, List<Char>> = depPairs.groupBy({ it.second }, { it.first }).toMutableMap()
        println("deps  $dependencies")

        val graph: Map<Char, List<Char>> = stepToNextSteps.mapValues { it.value.sorted() }
        println("graph $graph")

        var hasDependents = true

//        println("graph.values ${graph.values.flatten()}")
//        println("graph.values.distinct ${graph.values.flatten().toSortedSet()}")
//        println("graph.keys ${graph.keys.toSortedSet()}")
        println("minus ${graph.keys.toSortedSet().minus(graph.values.flatten().toSortedSet())}")
        val startNode: Char = graph.keys.toSortedSet().minus(graph.values.flatten().toSortedSet()).elementAt(0)
        var node = startNode
        var visited = emptyList<Char>()
        var currentEdges: List<Char> = graph.keys.toSortedSet().minus(graph.values.flatten().toSortedSet()).drop(1)
        currentEdges.forEach {
            dependencies[it] = emptyList()
        }
        fun allDependenciesVisited(node: Char): Boolean {
            val res = dependencies[node]!!.all { visited.contains(it) }
//            println("Are all of node $node's dependencies [${dependencies[node]}] visited [$visited] = $res")
            return res
        }

        while (hasDependents) {
            visited += node
            dependencies = dependencies.mapValues { entry -> entry.value.filterNot { node == it } }.toMutableMap()
            val edges = graph.getOrDefault(node, emptySet<Char>())
            if (edges.isEmpty()) {
                hasDependents = false
            } else {
                currentEdges = currentEdges.plus(edges).sorted()
                node = currentEdges.filter { allDependenciesVisited(it) }.getOrElse(0) { currentEdges.get(it) }
                currentEdges = currentEdges.filterNot { node == it }
            }
        }
        println("part one = ${visited.joinToString(separator = "")}")

        println("*******")
//        bfs(startNode, graph, dependencies)
    }

    fun bfs(root: String, graph: Map<String, List<String>>, deps: Map<String, List<String>>) {
        var visited = listOf<String>()
        var currentAdjacents: List<String> = listOf()
        var queue = sortedSetOf<String>()
        queue.add(root)

        while (queue.isNotEmpty()) {
            println("visited $visited; queue $queue")
            val node: String = queue.first()
            queue.remove(node)

            val adjacents = graph[node].orEmpty()
            visited += node
            currentAdjacents += adjacents
            currentAdjacents = currentAdjacents.sorted()

            fun allDepsVisited(node: String): Boolean {
                return deps[node]!!.all { visited.contains(it) }
            }

            for (n in currentAdjacents) {
                if (!visited.contains(n) && !queue.contains(n) && allDepsVisited(n)) {
                    queue.add(n)
                }
            }
        }
        println(visited.joinToString(""))
        // BGKDMJCNESTUZWY

    }


    // CABDFE

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = listOf(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
        )

        // BGKDMJCNEUZWY : incorrect
        // BGKDMJCNEUZWYAFHILIO : incorrect
        // QFIO : incorrect
        // RAFHILIO : incorrect
        // QSTLPAFIVXO : incorrect

        // including Q and R in initial set of current edges...
        // BGKDMJCNEQRSTUZWHYLPAFIVXO
        Runner.timedRun(7) { input ->
            run(input)
        }
    }
}