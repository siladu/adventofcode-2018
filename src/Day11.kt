import kotlin.test.assertEquals

object Day11 {

    data class Point(val x: Int, val y: Int)

    private fun run(input: Int) {
        assertEquals(powerLevel(Point(3, 5), 8), 4)
        assertEquals(powerLevel(Point(122,79), 57), -5)
        assertEquals(powerLevel(Point(217,196), 39), 0)
        assertEquals(powerLevel(Point(101,153), 71), 4)

        println("****************")
        val grid = buildGrid(input)

        val maxForWindow3 = maxForWindow(3, grid)
        println("PART ONE $input = ${maxForWindow3.key.x},${maxForWindow3.key.y} with value ${maxForWindow3.value}")

        val maxEntryAndWindow = (1..25).map { windowSize ->
            val maxEntryForWindow = maxForWindow(windowSize, grid)
//            println("$input = ${maxEntryForWindow.key.x},${maxEntryForWindow.key.y},$windowSize with value ${maxEntryForWindow.value}")
            Pair(maxEntryForWindow, windowSize)
        }.maxBy { it.first.value }!!

        val maxEntry = maxEntryAndWindow.first
        val windowSize = maxEntryAndWindow.second
        println("PART TWO $input = ${maxEntry.key.x},${maxEntry.key.y},$windowSize with value ${maxEntry.value}")
    }

    private fun maxForWindow(windowSize: Int, grid: Array<IntArray> ): Map.Entry<Point, Int> {
        // Why is this function inefficient for large windowSize?

        var map = mutableMapOf<Point, Int>()

        for (y in (1..(gridSize - windowSize))) {
            for (x in (1..(gridSize - windowSize))) {

                val windowPower = windowPower(Point(x, y), windowSize, grid)
                map[Point(x, y)] = windowPower
            }
        }

        return map.maxBy { it.value }!!
    }

    private fun windowPower(p: Point, windowSize: Int, grid: Array<IntArray>): Int {

        return (0 until windowSize).map { yOffset ->
            (0 until windowSize).map { xOffset ->
                grid[p.x + xOffset][p.y + yOffset]
            }.sum()
        }.sum()
    }

    private fun buildGrid(serial: Int): Array<IntArray> {
        val grid: Array<IntArray> = Array(gridSize + 1) { IntArray(gridSize + 1) }

        for (y in (1..gridSize)) {
            for (x in (1..gridSize)) {
                grid[x][y] = powerLevel(Point(x, y), serial)
            }
        }
        return grid
    }

    private fun powerLevel(p: Point, serial: Int): Int {
        val rackId = p.x + 10
        var power = rackId * p.y
        power += serial
        power *= rackId
        power = if (power > 100) power.toString().takeLast(3).dropLast(2).toInt() else 0
        return power - 5
    }

    private fun printGrid(grid: Array<IntArray>) {
        fun padding(coord: Int) = (1..(3 - coord.toString().length)).map { " " }.joinToString("")

        (0..gridSize).forEach { print(padding(it) + it) }
        println()
        for (y in (1..gridSize)) {
            print(padding(y) + y + " ")
            for (x in (1..gridSize)) {
                val prefix = if (grid[x][y] < 0) "" else " "
                print(prefix + grid[x][y].toString() + " ")
            }
            println()
        }
        (0..gridSize).forEach { print(padding(it) + it) }
        println()
    }

    const val gridSize = 300

    @JvmStatic
    fun main(args: Array<String>) {

        Runner.timedRun(11) { _ ->
            run(18) // PART ONE 18 = 33,45 with value 29
                    // PART TWO 18 = 90,269,16 with value 113
        }
        Runner.timedRun(11) {
            run(42) // PART ONE 42 = 21,61 with value 30
                    // PART TWO 42 = 232,251,12 with value 119
        }
        Runner.timedRun(11) {
            run(5177) // PART ONE 5177 = 235,22 with value 30
                      // PART TWO 5177 = 231,135,8 with value 80
        }
    }
}