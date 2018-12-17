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

    private fun run(input: Int, partTwoInput: String) {
        val elf1 = Elf(0, 3)
        val elf2 = Elf(1, 7)
        val output: MutableList<Int> = mutableListOf(3, 7)
        fun move(elf: Elf) = (1 + elf.score + elf.pos) % output.size

        val max = input + 10
        for (i in 1..max) {

            val fromIndex = kotlin.math.max(i - partTwoInput.length, 0)
            val trimmedOutput: String = output.subList(fromIndex, i).joinToString("")
            if (trimmedOutput == partTwoInput) {
                println("PART TWO $fromIndex")
            }

            val sum = elf1.score + elf2.score
            val sumAsString: List<Int> = sum.toString().map { it.toString().toInt() }
            output.addAll(sumAsString)
            elf1.pos = move(elf1)
            elf1.score = output[elf1.pos]

            elf2.pos = move(elf2)
            elf2.score = output[elf2.pos]

        }
        println("PART ONE " + output.drop(input).take(10).joinToString(""))
    }

    @JvmStatic
    fun main(args: Array<String>) {

        /*
        51589 first appears after 9 recipes.
        01245 first appears after 5 recipes.
        92510 first appears after 18 recipes.
        59414 first appears after 2018 recipes.
         */
        Runner.timedRun(14) { input ->
//            run(9, "51589") //0124515891
//            run(5, "01245") //0124515891
//            run(18, "92510") //9251071085
//            run(2018, "59414") //5941429882
//            run(846601, "846601") //3811491411
            run(20408083, "846601") //20408083
        }
    }
}