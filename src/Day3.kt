
typealias Point = Pair<Int, Int>

object Day3 {

    private val pointMap = mutableMapOf<Point, Int>()
    private val uniqueCollisions = mutableMapOf<Point, String>()

    private fun partOne(input: List<String>) {

        input.forEach{ pointsForClaim(parseClaim(it)) }

        //112538 is too low
        //116521
        //147386 is too high
        println(uniqueCollisions.size)
    }

    private fun pointsForClaim(claim: Claim) {
        for (heightOffset in (0 until claim.height)) {
            for (widthOffset in (0 until claim.width)) {
                val point = Pair(claim.left + widthOffset, claim.top + heightOffset)
                if (pointMap.put(point, claim.id) != null) {
                    uniqueCollisions.put(point, "X")
                }
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
        val testInput3 = listOf(
            "#1 @ 1,3: 4x4",
            "#2 @ 3,1: 4x4",
            "#3 @ 5,5: 2x2"
        )
        val testInput2 = listOf(
            "#1 @ 1,1: 5x5",
            "#2 @ 2,2: 5x5",
            "#3 @ 3,3: 5x5",
            "#4 @ 4,4: 5x5"
        )

        Runner.timedRun("day3-input.txt") { input ->
            print("testInput1 overlapping cells = ")
            partOne(testInput1)
            visualiseTestInput()

            pointMap.clear()
            uniqueCollisions.clear()
            print("\ntestInput2 overlapping cells = ")
            partOne(testInput2)
            visualiseTestInput()
            println()

            pointMap.clear()
            uniqueCollisions.clear()
            print("\ntestInput3 overlapping cells = ")
            partOne(testInput3)
            visualiseTestInput()
            println()

            print("\ninput overlapping cells = ")
            partOne(input)
        }
    }
}