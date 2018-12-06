
typealias Point = Pair<Int, Int>

object Day3 {

    private val pointMap = mutableMapOf<Point, Int>()
    private val uniqueCollisions = mutableMapOf<Point, String>()

    private fun run(input: List<String>) {

        val claimsWithPoints: List<Pair<Claim, List<Point>>> = input.map { pointsForClaim(parseClaim(it)) }
        claimsWithPoints.forEach { (claim, points) -> populateMapsForClaimPoints(claim, points) }

        println("part one: num overlapping cells = ${uniqueCollisions.size}")

        println("part two: non-overlapping claimId: " + claimsWithPoints.filter { (claim, points) -> !points.any { uniqueCollisions.containsKey(it) } }.map { it.first.id })
    }

    private fun pointsForClaim(claim: Claim): Pair<Claim, List<Point>> {
        val points = arrayListOf<Point>()
        for (heightOffset in (0 until claim.height)) {
            for (widthOffset in (0 until claim.width)) {
                val point = Pair(claim.left + widthOffset, claim.top + heightOffset)
                points.add(point)
            }
        }
        return Pair(claim, points)
    }

    private fun populateMapsForClaimPoints(claim: Claim, points: List<Point>) {
        points.forEach { point ->
            if (pointMap.put(point, claim.id) != null) {
                uniqueCollisions.put(point, "X")
            }
        }
    }

    data class Claim(val id: Int, val left: Int, val top: Int, val width: Int, val height: Int)

    private fun parseClaim(rawClaim: String): Claim {
        val split = rawClaim.split(' ')
        val positions = split[2].split(',')
        val dimensions = split[3].split('x')

        val id = split[0].removePrefix("#").toInt()
        val left = positions[0].toInt()
        val top = positions[1].removeSuffix(":").toInt()
        val width = dimensions[0].toInt()
        val height = dimensions[1].toInt()

        return Claim(id, left, top, width, height)
    }

    private fun visualiseTestInput() {
        for (y in (0 until 10)) {
            for (x in (0 until 10)) {
                val point = Pair(x, y)
                print(uniqueCollisions.getOrDefault(point, pointMap.getOrDefault(point, ".")))
            }
            println()
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput1 = listOf(
            "#1 @ 3,2: 5x4"
        )
        val testInput2 = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        val testInput3 = listOf(
            "#1 @ 1,1: 5x5",
            "#2 @ 2,2: 5x5",
            "#3 @ 3,3: 5x5",
            "#4 @ 4,4: 5x5"
        )

        Runner.timedRun(3) { input ->
//            println("testInput1:")
//            run(testInput1)
//            visualiseTestInput()
//
//            resetState()
//            println("\ntestInput2:")
//            run(testInput2)
//            visualiseTestInput()
//
//            println()
//
//            resetState()
//            println("\ntestInput3:")
//            run(testInput3)
//            visualiseTestInput()
//
//            resetState()
//            println("\ninput:")
            run(input)
        }
    }

    private fun resetState() {
        pointMap.clear()
        uniqueCollisions.clear()
    }
}