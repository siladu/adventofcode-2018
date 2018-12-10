object Day10 {

    data class Point(val x: Int, val y: Int)
    data class Velocity(val dx: Int, val dy: Int)
    data class PointAndVelocity(val point: Point, val velocity: Velocity)
    data class Grid(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int, val xyDistance: Int)
    data class Frame(val second: Int, val grid: Grid, val points: List<Point>)

    private fun run(input: List<String>) {

        var pointsAndVelocities = input.map { parseLine(it) }
        var frames: MutableList<Frame> = mutableListOf()

        for (second in (1..11000)) {
            pointsAndVelocities = pointsAndVelocities.map { translatePoint(it) }
            val newPoints = pointsAndVelocities.map { it.point }

            val grid = calculateGridSize(newPoints)
            val frame = Frame(second, grid, newPoints)
            frames.add(frame)
        }

        val minFrame: Frame = frames.minBy { it.grid.xyDistance }!!
        println("${minFrame.second} seconds, ${minFrame.grid.xyDistance} xy-distance")
        printFrame(frames.minBy { it.grid.xyDistance }!!)
    }

    private fun calculateGridSize(points: List<Point>): Grid {
        val minX = points.map { it.x }.min()!!
        val maxX = points.map { it.x }.max()!!
        val minY = points.map { it.y }.min()!!
        val maxY = points.map { it.y }.max()!!
        val xDistance = maxX - minX
        val yDistance = maxY - minY
        val xyDistance = xDistance + yDistance
        return Grid(minX, maxX, minY, maxY, xyDistance)
    }

    private fun printFrame(frame: Frame) {
        val grid = frame.grid

        for (y in (grid.minY..grid.maxY)) {
            for (x in (grid.minX..grid.maxX)) {

                if (frame.points.contains(Point(x, y)))
                    print("#")
                else
                    print(".")
            }
            println()
        }
        println()
    }

    private fun translatePoint(pav: PointAndVelocity): PointAndVelocity {
        return PointAndVelocity(
            Point(pav.point.x + pav.velocity.dx, pav.point.y + pav.velocity.dy),
            pav.velocity
        )
    }

    private fun parseLine(input: String): PointAndVelocity {
        val split = input.split("""<|>""".toRegex())
        val rawPos = split[1]
        val rawVel = split[3]
        val position = Point(parseInt(rawPos.split(",")[0]), parseInt(rawPos.split(",")[1]))
        val velocity = Velocity(parseInt(rawVel.split(",")[0]), parseInt(rawVel.split(",")[1]))
        return PointAndVelocity(position, velocity)
    }

    private fun parseInt(raw: String) = raw.trim().toInt()

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = listOf(
            "position=< 9,  1> velocity=< 0,  2>",
            "position=< 7,  0> velocity=<-1,  0>",
            "position=< 3, -2> velocity=<-1,  1>",
            "position=< 6, 10> velocity=<-2, -1>",
            "position=< 2, -4> velocity=< 2,  2>",
            "position=<-6, 10> velocity=< 2, -2>",
            "position=< 1,  8> velocity=< 1, -1>",
            "position=< 1,  7> velocity=< 1,  0>",
            "position=<-3, 11> velocity=< 1, -2>",
            "position=< 7,  6> velocity=<-1, -1>",
            "position=<-2,  3> velocity=< 1,  0>",
            "position=<-4,  3> velocity=< 2,  0>",
            "position=<10, -3> velocity=<-1,  1>",
            "position=< 5, 11> velocity=< 1, -2>",
            "position=< 4,  7> velocity=< 0, -1>",
            "position=< 8, -2> velocity=< 0,  1>",
            "position=<15,  0> velocity=<-2,  0>",
            "position=< 1,  6> velocity=< 1,  0>",
            "position=< 8,  9> velocity=< 0, -1>",
            "position=< 3,  3> velocity=<-1,  1>",
            "position=< 0,  5> velocity=< 0, -1>",
            "position=<-2,  2> velocity=< 2,  0>",
            "position=< 5, -2> velocity=< 1,  2>",
            "position=< 1,  4> velocity=< 2,  1>",
            "position=<-2,  7> velocity=< 2, -2>",
            "position=< 3,  6> velocity=<-1, -1>",
            "position=< 5,  0> velocity=< 1,  0>",
            "position=<-6,  0> velocity=< 2,  0>",
            "position=< 5,  9> velocity=< 1, -2>",
            "position=<14,  7> velocity=<-2,  0>",
            "position=<-3,  6> velocity=< 2, -1>"
        )

        Runner.timedRun(10) { input ->

            run(input)
        }
    }
}