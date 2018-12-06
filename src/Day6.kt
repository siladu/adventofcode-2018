import kotlin.math.abs

object Day6 {

    private fun run(input: List<String>, gridSize: Int, safeAreaThreshold: Int, visualise: Boolean) {

        val points = input.map {
            val (x, y) = """(\d+), (\d+)""".toRegex().find(it)!!.destructured
            Point(x.toInt(), y.toInt())
        }

        fun manhattan(a: Point, b: Point) =  abs(a.first - b.first) + abs(a.second - b.second)
        fun isTouchingEdge(point: Point): Boolean = point.toList().any { listOf(0, gridSize - 1).contains(it) }
        var pointsTouchingEdge: Set<Point> = emptySet()
        var pointName = 'A'
        val pointToChars: Map<Point, Char> = points.associate { it to pointName++ }
        val pointToArea: MutableMap<Point, Int> = points.associate { it to 0 }.toMutableMap()
        var safeArea = 0

        for (y in (0 until gridSize)) {
            for (x in (0 until gridSize)) {
                val cell = Pair(x, y)
                val pointToDistance: Map<Point, Int> = points.associate { it to manhattan(it, cell) }
                val pointsOfMinDistance: Set<Point> = pointToDistance.filter { it.value == pointToDistance.values.min() }.keys

                // inefficient - the same point is added for every cell in its area
                // better would be to have Map<Point, List<Cell>>
                // and filterNot any points associated with cells touching the edge
                if (pointsOfMinDistance.size == 1 && isTouchingEdge(cell)) {
                    pointsTouchingEdge += pointsOfMinDistance.first()
                }

                val currentAreaSize = pointToArea.getOrDefault(pointsOfMinDistance.first(), 0)
                when {
                    points.contains(cell) -> pointToArea[cell] = currentAreaSize + 1
                    pointsOfMinDistance.size == 1 -> pointToArea[pointsOfMinDistance.first()] = currentAreaSize + 1
                }

                if (visualise) {
                    visualiseCell(pointsOfMinDistance, points, cell, pointToChars)
                }

                // part two
                val manhattanSum = points.sumBy { manhattan(it, cell) }
                if (manhattanSum < safeAreaThreshold) {
                    safeArea++
                }
            }
            if (visualise) {
                println()
            }
        }

        val finitePoints: Map<Point, Int> = pointToArea.filterNot { (k, v) -> pointsTouchingEdge.contains(k) }
        println("part one = " + finitePoints.values.max())
        println("part two = $safeArea")
    }

    private fun visualiseCell(
        pointsWithMinDistance: Set<Point>,
        points: List<Point>,
        cell: Pair<Int, Int>,
        pointToChars: Map<Point, Char>
    ) {
        val cellContents =
            when {
                points.contains(cell) -> pointToChars[cell]
                pointsWithMinDistance.size == 1 -> pointToChars[pointsWithMinDistance.first()]!!.toLowerCase()
                else -> "."
            }
        print(cellContents)
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = listOf(
            "1, 1",
            "1, 6",
            "8, 3",
            "3, 4",
            "5, 5",
            "8, 9"
        )

        Runner.timedRun(6) { input ->
            println("TEST INPUT")
            run(testInput, 10, 32, true)
            println()
            println("INPUT")
            run(input, 360, 10000, false)
        }
    }
}