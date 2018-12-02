import java.util.HashMap

object Day1 {

    private fun partOne(input: List<String>) {
        println("Part One = ${ input.sumBy { it.toInt() } }")
    }

    private fun partTwo(input: List<String>) {

        val previousFrequencies = HashMap<Int, Boolean>()

        var frequency = 0
        var count = -1
        var shouldFinish = false
        while (!shouldFinish) {

            if (count++ == input.size - 1) {
                count = 0
            }

            val line = input[count]
            val op = line[0]
            val value = Integer.parseInt(line.substring(1))
            when (op) {
                '+' -> frequency += value
                '-' -> frequency -= value
            }

            val result = previousFrequencies.put(frequency, true)
            if (result != null) {
                shouldFinish = true
            }
        }
        println("Part Two = $frequency")
    }

    @JvmStatic
    fun main(args: Array<String>) {
        Runner.timedRun("day1-input.txt") {
            partOne(it)
            partTwo(it)
        }
    }
}