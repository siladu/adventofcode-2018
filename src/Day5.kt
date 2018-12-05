import kotlin.streams.toList

object Day5 {

    private fun run(input: String): Int {

        var polymer = input
        var idx = 0
        while(idx < polymer.length) {

            if (togglePolarity(polymer[idx]) == polymer.getOrNull(idx - 1)) {
                polymer = polymer.removeRange(idx - 1, idx + 1)
                idx -= 2
            }

            idx++
        }

        return polymer.length
    }

    private fun partTwo(input: String) {

        val min = ('a'..'z').toList().parallelStream().map { type ->
            run(input.filterNot { it.toLowerCase() == type })
        }.toList().min()

        println("part two = $min")
    }

    private fun togglePolarity(unit: Char): Char = if (unit.isLowerCase()) unit.toUpperCase() else unit.toLowerCase()

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = "dabAcCaCBAcCcaDA" // -> "dabCBAcaDA" / 4

        Runner.timedRun("day5-input.txt") { input ->
            println("part one = " + run(input[0]))
            partTwo(input[0])
        }
    }

}