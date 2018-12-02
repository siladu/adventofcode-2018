import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.HashMap

object Day1Part2 {

    private fun run() {
        val start = System.nanoTime()
        val path = Paths.get("day1-input.txt")
        val input = Files.readAllLines(path)
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
        println("RESULT = $frequency")
        val timeInNanos = System.nanoTime() - start
        println("Time in nanos: $timeInNanos")
        println("Time in millis: " + timeInNanos / Math.pow(10.0, 6.0))
        println("Time in seconds: " + timeInNanos / Math.pow(10.0, 9.0))
    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        run()
    }
}