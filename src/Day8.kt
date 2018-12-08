object Day8 {

    private fun run(input: String) {
        val numbers = input.split(" ").map { it.toInt() }
        val rootHeader = numbers.take(2)
        val inputWithoutRoot = numbers.drop(2)
        val startNode = TreeNode(rootHeader[0], rootHeader[1])

        var cursor = 0
        var metaSum = 0

        fun dfs(root: TreeNode): TreeNode {

            fun recordMeta(root: TreeNode) {
                root.meta = inputWithoutRoot.drop(cursor).take(root.numMeta)
                cursor += root.numMeta
                metaSum += root.meta.sum()
            }

            if (root.numChildren == 0) {
                recordMeta(root)
                root.value = root.meta.sum()
                return root
            }

            for (i in (0 until root.numChildren)) {
                val header = inputWithoutRoot.drop(cursor).take(2)
                cursor += 2
                val childNode = TreeNode(header[0], header[1])
                root.addChild(childNode)
                dfs(childNode)
            }

            recordMeta(root)
            root.value = root.meta.sumBy { root.children.getOrNull(it - 1)?.value ?: 0 }
            return root
        }

        val root = dfs(startNode)
        println("part one = $metaSum")
        println("part two = ${root.value}")
    }

    //https://github.com/gazolla/Kotlin-Algorithm
    class TreeNode(c: Int, m: Int){
        var value: Int = 0
        var numChildren: Int = c
        var numMeta: Int = m
        var meta = listOf<Int>()
        var parent: TreeNode? = null
        var children: MutableList<TreeNode> = mutableListOf()

        fun addChild(node: TreeNode) {
            children.add(node)
            node.parent = this
        }

        override fun toString(): String {
            var s = "($numChildren, $numMeta, $meta, $value)"
            if (!children.isEmpty()) {
                s += " {" + children.map { it.toString() } + " }"
            }
            return s
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2"
//        val testInput = "1 3 0 3 10 11 12 1 1 2"

        Runner.timedRun(8) { input ->
            run(input[0])
        }
    }
}