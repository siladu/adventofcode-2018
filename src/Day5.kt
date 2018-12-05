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
        val distinctTypes = input.toLowerCase().groupBy { it }.keys.sorted()

        val polymerLengths: List<Int> = distinctTypes.map {

            val partiallyReplaced = input.replace(it.toString(), "")
            val newInput = partiallyReplaced.replace(it.toUpperCase().toString(), "")
            run(newInput)
        }
        println("part two = " + polymerLengths.sorted().first())
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