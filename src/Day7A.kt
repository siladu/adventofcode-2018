object Day7A {

    private fun run(input: List<String>) {
        // https://en.wikipedia.org/wiki/Topological_sorting#Kahn's_algorithm
        val pairs = input.map { it.split(" ") }.map { Pair(it[1], it[7]) }
        val graph: MutableMap<String, MutableList<String>> = pairs.groupBy({ it.first }, { it.second }).mapValues { it.value.toMutableList() }.toMutableMap()

//        L ← Empty list that will contain the sorted elements
        val sorted = mutableListOf<String>()
//        S ← Set of all nodes with no incoming edge
        val startNodes: MutableSet<String> = graph.keys.filter { n -> graph.values.none { it.contains(n) } }.toSortedSet()

//        while S is non-empty do
        while (startNodes.isNotEmpty()) {
            val node = startNodes.first()
//            remove a node n from S
            startNodes.remove(node)
//            add n to tail of L
            sorted.add(node)
            val edges: List<String> = graph.getOrDefault(node, emptyList<String>()).toList() //copy the list
//            for each node m with an edge e from n to m do
            edges.forEach { edge ->
//              remove edge e from the graph
                graph[node]?.remove(edge)

//              if m has no other incoming edges then
                if (graph.values.none { it.contains(edge) }) {
//                  insert m into S
                    startNodes.add(edge)
                }
            }
        }

        println("PART ONE = ${sorted.joinToString("")}")
    }

    private fun partTwo(input: List<String>, numWorkers: Int, additionalTime: Int, numUniqueChars: Int) {
        val pairs = input.map { it.split(" ") }.map { Pair(it[1], it[7]) }
        val graph: MutableMap<String, MutableList<String>> = pairs.groupBy({ it.first }, { it.second }).mapValues { it.value.toMutableList() }.toMutableMap()
        val sorted = mutableListOf<String>()
        val startNodes: MutableSet<String> = graph.keys.filter { n -> graph.values.none { it.contains(n) } }.toSortedSet()
        data class Work(var item: String, var remaining: Int)
        data class Worker(var work: Work? = null) {
            fun isFree(): Boolean = work == null
        }

        var totalTime = 0
        val workers: List<Worker> = (1..numWorkers).map { Worker() }.toList()

        fun calculateRemaining(node: Char) = (node.toInt() % 65) + 1 + additionalTime

        fun printWorkers() {
            val workerString: String = workers.joinToString(" | ") { it.work?.item ?: "." }
            println("$totalTime | $workerString")
        }

        while(sorted.size < numUniqueChars) {

            val completedSteps = workers.filter { w -> w.work?.remaining == 0 }

            val preFilteredAvailableSteps: Set<String> = completedSteps
                .flatMap { graph[it.work?.item].orEmpty() }
                .toSortedSet()

            completedSteps.forEach { w -> graph.remove(w.work?.item) }
            completedSteps.forEach { w -> w.work = null }

            val availableSteps = preFilteredAvailableSteps
                .filterNot { n -> graph.values.any { it.contains(n) } }
            startNodes += availableSteps

            while (workers.any { it.isFree() } && !startNodes.isEmpty()) {
                val nextFreeItem = startNodes.first()
                startNodes.remove(nextFreeItem)
                sorted += nextFreeItem
                workers.first { it.isFree() }.work = Work(nextFreeItem, calculateRemaining(nextFreeItem[0]))
            }

            printWorkers()
            // tick busy workers
            workers.filterNot { it.isFree() }.forEach { it.work!!.remaining-- }
            totalTime++
        }
        while (workers.any { !it.isFree() }) {
            // tick remaining busy workers
            printWorkers()
            workers.filterNot { it.isFree() }.forEach { it.work!!.remaining-- }
            val completedSteps = workers.filter { w -> w.work?.remaining == 0 }
            completedSteps.forEach { w -> w.work = null }
            totalTime++
        }
        println("PART TWO = $totalTime")
    }

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

        // CABDFE
        // BGKDMJCNEQRSTUZWHYLPAFIVXO
        Runner.timedRun(7) { input ->
            run(input)
//            partTwo(testInput, 2, 0, 6) //15
            partTwo(input, 5, 60, 26) // 941
        }
    }
}