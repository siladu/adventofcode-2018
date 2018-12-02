import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

object Day2 {

    private fun runPartOne(input: List<String>) {

        fun matchesFor(word: String): Set<Int> {
            return word
                .groupBy { it }
                .entries.groupBy({ it.value.size }, { it.value[0] })
                .filterKeys { it in 2..3 }
                .keys
        }

        val twosAndThrees = input.map(::matchesFor)
        val theTwos = twosAndThrees.filter { it.contains(2) }
        val theThrees = twosAndThrees.filter { it.contains(3) }
        println(theTwos.size * theThrees.size)
    }

    data class Comparision(val word: String, val other: String, val commonLetters: List<Char>, val common: Int, val diff: Int)

    private fun runPartTwo(input: List<String>) {

        fun letterComparison(word: String, other: String): Comparision {
            val common = word.zip(other).filter { it.first == it.second }.map { it.first }
            return Comparision(word, other, common, common.size, word.length - common.size)
        }

        val cartesianProduct: List<Comparision> = input.flatMap { word -> input.map { letterComparison(word, it) } }
        val onlyOneLetterDifferent = cartesianProduct.filter { it.diff == 1 }
        val result = onlyOneLetterDifferent[0].commonLetters.joinToString("")
        println(result)
    }

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val start = System.nanoTime()
        val path = Paths.get("day2-input.txt")
        val input = Files.readAllLines(path)

        runPartOne(input)
        runPartTwo(input)

        val timeInNanos = System.nanoTime() - start
        println("Time in nanos: $timeInNanos")
        println("Time in millis: " + timeInNanos / Math.pow(10.0, 6.0))
        println("Time in seconds: " + timeInNanos / Math.pow(10.0, 9.0))
    }
}