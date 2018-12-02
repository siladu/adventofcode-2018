import java.io.File

object Runner {

    fun timedRun(inputFile: String, run: (List<String>) -> Unit) {
        val start = System.nanoTime()
        val input = File(inputFile).readLines()

        run(input)

        val timeInNanos = System.nanoTime() - start
        println("Time in nanos: $timeInNanos")
        println("Time in millis: " + timeInNanos / Math.pow(10.0, 6.0))
        println("Time in seconds: " + timeInNanos / Math.pow(10.0, 9.0))
    }
}