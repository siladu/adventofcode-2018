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
        val output: MutableList<Int> = mutableListOf(3, 7)
        fun move(elf: Elf) = (1 + elf.score + elf.pos) % output.size

        val max = input + 10
        for (i in 1..max) {
            val sum = elf1.score + elf2.score
            val sumAsString: List<Int> = sum.toString().map { it.toString().toInt() }
            output.addAll(sumAsString)
            elf1.pos = move(elf1)
            elf1.score = output[elf1.pos]

            elf2.pos = move(elf2)
            elf2.score = output[elf2.pos]

//            println(i)
        }
        println("RES " + output.drop(input).take(10).joinToString(""))
    }

    @JvmStatic
    fun main(args: Array<String>) {

        Runner.timedRun(14) { input ->
//            run(5) //0124515891
//            run(18) //9251071085
//            run(2018) //5941429882
            run(846601) //3811491411
        }
    }
}