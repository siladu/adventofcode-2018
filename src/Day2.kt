object Day2 {

    private fun partOne(input: List<String>) {

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

    private fun partOneImproved(input: List<String>) {
        // https://www.reddit.com/r/adventofcode/comments/a2aimr/2018_day_2_solutions/eawrg2y
        val charCounts: List<Collection<Int>> = input.map { word ->
            word.groupingBy { it }.eachCount().values
        }

        val twos = charCounts.count { 2 in it }
        val threes = charCounts.count { 3 in it }
        println(twos * threes)
    }

    data class Comparision(val word: String, val other: String, val commonLetters: List<Char>, val diff: Int)

    private fun partTwo(input: List<String>) {

        fun letterComparison(word: String, other: String): Comparision {
            val common = word.zip(other).filter { it.first == it.second }.map { it.first }
            return Comparision(word, other, common, word.length - common.size)
        }

        val cartesianProduct: List<Comparision> = input.flatMap { word -> input.map { letterComparison(word, it) } }
        val onlyOneLetterDifferent = cartesianProduct.filter { it.diff == 1 }
        val result = onlyOneLetterDifferent[0].commonLetters.joinToString("")
        //umdryabviapkozistwcnihjqx
        println(result)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        Runner.timedRun("day2-input.txt") {
            partOne(it)
            partOneImproved(it)
            partTwo(it)
        }
    }
}