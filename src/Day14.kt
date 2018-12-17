import java.util.*

object Day14 {

    /*
    (3)[7]
    (3)[7] 1  0
     3  7  1 [0](1) 0
     3  7  1  0 [1] 0 (1)
    (3) 7  1  0  1  0 [1] 2
     3  7  1  0 (1) 0  1  2 [4]
     3  7  1 [0] 1  0 (1) 2  4  5
     3  7  1  0 [1] 0  1  2 (4) 5  1
     3 (7) 1  0  1  0 [1] 2  4  5  1  5
     3  7  1  0  1  0  1  2 [4](5) 1  5  8
     3 (7) 1  0  1  0  1  2  4  5  1  5  8 [9]
     3  7  1  0  1  0  1 [2] 4 (5) 1  5  8  9  1  6
     3  7  1  0  1  0  1  2  4  5 [1] 5  8  9  1 (6) 7
     3  7  1  0 (1) 0  1  2  4  5  1  5 [8] 9  1  6  7  7
     3  7 [1] 0  1  0 (1) 2  4  5  1  5  8  9  1  6  7  7  9
     3  7  1  0 [1] 0  1  2 (4) 5  1  5  8  9  1  6  7  7  9  2
     */

    data class Elf(var pos: Int, var score: Int)
    private fun run(input: Int) {
        val elf1 = Elf(0, 3)
        val elf2 = Elf(1, 7)
//        var output = listOf(3, 7)
        val output: LinkedList<Int> = LinkedList()
        output.add(3)
        output.add(7)
        var elf1Iterator = output.listIterator()
        var elf2Iterator = output.listIterator(1)
        fun move(elf: Elf) = (1 + elf.score + elf.pos) % output.size
        fun moveBy(elf: Elf) = 1 + elf.score

        for (i in 1..846611) {
            val sum = elf1.score + elf2.score
            val sumAsString: List<Int> = sum.toString().map { it.toString().toInt() }
            val endIterator = output.listIterator(output.size)
            sumAsString.forEach {
                endIterator.add(it)
            }
//            output.addAll(sumAsString)
//            println(output)
            elf1Iterator = output.listIterator(elf1.pos)
            elf1Iterator.next()
            elf1.pos = move(elf1)
            var elf1Score = 0
            for (j in (1..moveBy(elf1))) {
                if (!elf1Iterator.hasNext()) elf1Iterator = output.listIterator()
                elf1Score = elf1Iterator.next()
//                println("elf1 moveBy = $i to score $elf1Score")
            }
            elf1.score = elf1Score
//            println(elf1)

            elf2Iterator = output.listIterator(elf2.pos)
            elf2Iterator.next()
            elf2.pos = move(elf2)
            var elf2Score = 0
            for (j in (1..moveBy(elf2))) {
                if (!elf2Iterator.hasNext()) elf2Iterator = output.listIterator()
                elf2Score = elf2Iterator.next()
//                println("elf2 moveBy = $i to score $elf2Score")
            }
            elf2.score = elf2Score
//            println(elf2)
            println(i)
        }
        println("RES " + output.drop(input).take(10))
    }

    @JvmStatic
    fun main(args: Array<String>) {

        Runner.timedRun(14) { input ->
//            run(5) //0124515891
//            run(18) //9251071085
//            run(2018) //5941429882
            run(846601) //
        }
    }
}