import java.util.*

object Day9 {

    private fun run(input: String) {
        val split = input.split(" ")
        val numPlayers = split[0].toInt()
        val lastMarbleValue = split[6].toInt()

//        var currentMarbleIdx = 0
        var currentPlayer = 0
        val marbles: LinkedList<Int> = LinkedList()
        marbles.add(0)
        var iterator = marbles.listIterator()

        var playerScores = (1..numPlayers).associate { (it to 0L) }.toMutableMap()
        println("$numPlayers players; last marble is worth $lastMarbleValue points")
        println("[-] ${printableMarbles(marbles, 0).joinToString()}")

        val addTimings = mutableListOf<Long>()
        var firstAddAverage = 0.0
        val removeTimings = mutableListOf<Long>()
        var firstRemoveAverage = 0.0

        for (marbleValue in (1..lastMarbleValue)) {

            if (marbleValue % 23 == 0) {
//                val toRemove = Math.floorMod((currentMarbleIdx - 7), marbles.size)
//                currentMarbleIdx = toRemove
//
                val start = System.nanoTime()
                for (i in (0..7)) {
                    if (!iterator.hasPrevious()) iterator = marbles.listIterator(marbles.size)
                    iterator.previous()
                }
                val removedMarble = iterator.next()
                iterator.previous()
                iterator.remove()
                iterator.next()
//                val removedMarble = marbles.removeAt(toRemove)
                removeTimings += System.nanoTime() - start
//
                val newScore = marbleValue + removedMarble
                val currentScore = playerScores[currentPlayer + 1] ?: 0
                playerScores[currentPlayer + 1] = currentScore + newScore
            } else {
//                val nextIndex = getNextIndex(currentMarbleIdx, marbles)

                val start = System.nanoTime()
                if (!iterator.hasNext()) iterator = marbles.listIterator(0)
                iterator.next()
                iterator.add(marbleValue)
//                marbles.add(nextIndex, marbleValue)
                addTimings += System.nanoTime() - start

//                currentMarbleIdx = nextIndex

            }

            if (marbleValue % 10000 == 0) {
                val addAvg = addTimings.average()
                val remAvg = removeTimings.average()
                if (marbleValue == 10000) {
                    firstAddAverage = addAvg
                    firstRemoveAverage = remAvg
                }
                println("add : $addAvg ${(addAvg / firstAddAverage).toInt()}X slower - at marble value $marbleValue")
                println("rem : $remAvg ${(remAvg / firstRemoveAverage).toInt()}X slower - at marble value $marbleValue")
            }

//            println("[${currentPlayer + 1}] ${printableMarbles(marbles, iterator.nextIndex() - 1).joinToString()}")

            currentPlayer = (currentPlayer + 1) % numPlayers
        }
        val finalAddAvg = addTimings.average()
        val finalRemAvg = removeTimings.average()
        println("final add : $finalAddAvg ${(finalAddAvg / firstAddAverage).toInt()}X slower")
        println("final rem : $finalRemAvg ${(finalRemAvg / firstRemoveAverage).toInt()}X slower")
        println(playerScores.maxBy { it.value })
    }

    private fun getNextIndex(currentMarbleIdx: Int, marbles: MutableList<Int>): Int {
        val newIndex = (currentMarbleIdx + 2) % marbles.size
        return if (newIndex == 0) marbles.size else newIndex
    }

    private fun printableMarbles(marbles: List<Int>, currentMarble: Int): List<String> =
        marbles.map { if (marbles.indexOf(it) == currentMarble) "($it)" else it.toString() }

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = "9 players; last marble is worth 25 points"
        val testInput1 = "10 players; last marble is worth 1618 points"//: high score is 8317"
        val testInput2 = "13 players; last marble is worth 7999 points"//: high score is 146373"
        val testInput3 = "17 players; last marble is worth 1104 points"//: high score is 2764"
        val testInput4 = "21 players; last marble is worth 6111 points"//: high score is 54718"
        val testInput5 = "30 players; last marble is worth 5807 points"//: high score is 37305"
        val input = "478 players; last marble is worth 71240 points" // 375465
        val partTwoTestInput = "478 players; last marble is worth 712400 points" //31212086
        val inputPartTwo = "478 players; last marble is worth 7124000 points" //3037741441

        Runner.timedRun(8) {
//            run(testInput)
//            run(testInput1)
//            run(testInput2)
//            run(testInput3)
//            run(testInput4)
//            run(testInput5)
//            run(input)
            run(inputPartTwo)
//            run(partTwoTestInput)
        }
    }
}