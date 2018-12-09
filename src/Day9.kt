import java.util.*

object Day9 {

    private fun run(input: String) {
        val split = input.split(" ")
        val numPlayers = split[0].toInt()
        val lastMarbleValue = split[6].toInt()

        val marbles: LinkedList<Int> = LinkedList()
        marbles.add(0)
        var iterator = marbles.listIterator()

        var currentPlayer = 0
        var playerScores = (1..numPlayers).associate { (it to 0L) }.toMutableMap()
        println("$numPlayers players; last marble is worth $lastMarbleValue points")

        for (marbleValue in (1..lastMarbleValue)) {

            if (marbleValue % 23 == 0) {

                for (i in (0..7)) {
                    if (!iterator.hasPrevious()) iterator = marbles.listIterator(marbles.size)
                    iterator.previous()
                }
                val removedMarble = iterator.next()
                iterator.previous()
                iterator.remove()
                iterator.next()

                val newScore = marbleValue + removedMarble
                val currentScore = playerScores[currentPlayer + 1] ?: 0
                playerScores[currentPlayer + 1] = currentScore + newScore

            } else {

                if (!iterator.hasNext()) iterator = marbles.listIterator(0)
                iterator.next()
                iterator.add(marbleValue)
            }

            currentPlayer = (currentPlayer + 1) % numPlayers
        }

        println(playerScores.maxBy { it.value })
    }

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