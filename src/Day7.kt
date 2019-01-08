import java.util.*

object Day7 {

    private fun run(input: List<String>, numWorkers: Int, additionalTime: Int) {

        val depPairs: List<Pair<Char, Char>> = input.map {
            val (dependency, step) = """Step (.) must be finished before step (.) can begin.""".toRegex().find(it)!!.destructured
            (dependency.toCharArray()[0] to step.toCharArray()[0])
        }

        println(depPairs)

        val stepToNextSteps: Map<Char, List<Char>> = depPairs.groupBy({ it.first }, { it.second })
        val staticDependencies: Map<Char, List<Char>> = depPairs.groupBy({ it.second }, { it.first }).toMap()
        var dependencies: MutableMap<Char, List<Char>> = depPairs.groupBy({ it.second }, { it.first }).toMutableMap()
        println("deps  $dependencies")

        val graph: Map<Char, List<Char>> = stepToNextSteps.mapValues { it.value.sorted() }
        println("graph $graph")

        println("minus ${graph.keys.toSortedSet().minus(graph.values.flatten().toSortedSet())}")
        val startNode: Char = graph.keys.toSortedSet().minus(graph.values.flatten().toSortedSet()).elementAt(0)

//        println("graph.values ${graph.values.flatten()}")
//        println("graph.values.distinct ${graph.values.flatten().toSortedSet()}")
//        println("graph.keys ${graph.keys.toSortedSet()}")

        var currentEdges: List<Char> = graph.keys.toSortedSet().minus(graph.values.flatten().toSortedSet()).drop(1)
        currentEdges.forEach {
            dependencies[it] = emptyList()
        }

        data class Work(var item: Char, var remaining: Int)
        data class Worker(var work: Work? = null) {
            fun isFree(): Boolean = work == null
        }

        var totalTime = 0
        val workers: List<Worker> = (1..numWorkers).map { Worker() }.toList()
        println(workers)


        fun calculateRemaining(node: Char) = (node.toInt() % 65) + 1 + additionalTime

        fun printWorkers() {
            val workerString = workers.joinToString(" | ") { (it.work?.item ?: '.').toString() }
            println("$totalTime | $workerString")
        }

        workers[0].work = Work(startNode, calculateRemaining(startNode))
        for (i in (1..currentEdges.size)) {
            workers[i].work = Work(currentEdges[i-1], calculateRemaining(currentEdges[i - 1]))
        }

        var visited = emptyList<Char>()
        var workCompleted = emptyList<Char>()
        var workVisited = emptyList<Char>()
        fun allDependenciesVisited(node: Char): Boolean {
            val res = dependencies[node]!!.all { visited.contains(it) }
//            println("Are all of node $node's dependencies [${dependencies[node]}] visited [$visited] = $res")
            return res
        }

        fun noDepsVisited(node: Char): Boolean = staticDependencies[node]!!.none { workVisited.contains(it) }

        fun anyDependenciesBeingWorkedOn(node: Char): Boolean {
            return staticDependencies[node]!!.any {
                dep -> workers.any { w -> w.work?.item == dep }
            }
        }

        val uniqueChars = staticDependencies.keys.plus(graph.keys).toSortedSet().toList()
        val numberUniqueChars = uniqueChars.size
        println("numberUniqueChars $numberUniqueChars")

        fun nodesAvailableForWork(): List<Char> {
            val exceptVisited = uniqueChars.minus(workVisited)
            val anyDepsBeingWorkedOn = exceptVisited.filterNot { anyDependenciesBeingWorkedOn(it) }
            val noneVisited = anyDepsBeingWorkedOn.filterNot { noDepsVisited(it) }
            return noneVisited
        }

        workVisited += startNode
        workVisited += currentEdges
        while (workCompleted.size < numberUniqueChars /*&& workers.all { it.isFree() }*/) {

//            println(workers)
            printWorkers()
            // tick busy workers
            workers.filterNot { it.isFree() }.forEach {
                it.work!!.remaining--
                if (it.work!!.remaining == 0) {
                    workCompleted += it.work?.item!!
                    it.work = null
                }
            }
//            println(workers)

            // while workers are free, add any available visited nodes
//            println("nodes available for work ${nodesAvailableForWork()}")
            while (workers.any { it.isFree() } && nodesAvailableForWork().isNotEmpty()) {
                val nextFreeItem = nodesAvailableForWork().first()
//                println("nextFreeItem $nextFreeItem")
                workVisited += nextFreeItem
                workers.first { it.isFree() }.work = Work(nextFreeItem, calculateRemaining(nextFreeItem))
            }

//            println("completed $workCompleted")

            ++totalTime
        }
        println(totalTime)
//        println("completed ${workCompleted.toSortedSet()}")

        var node: Char = startNode
        var hasDependents = true
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
        println("part two = $totalTime")
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
//            run(testInput, 2, 0)
            run(input, 5, 60) // 526, 527, 552 is too low
        }
    }
}