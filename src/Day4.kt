import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Day4 {

    private val guardSleeps = mutableMapOf<Int, List<Int>>()
    private val guardMinutes = mutableMapOf<Int, List<Int>>()

    fun runImproved(input: List<String>) {
        //https//github.com/janbina/advent-of-code-2018/blob/master/src/Day04.kt
    }

    private fun run(input: List<String>) {

        var currentGuardId = 0
        var currentSleepMinute = 0

        for (line in input) {
            val guardRegex = "\\[(.*)\\] Guard #([\\d]+) begins shift".toRegex()
            val sleepRegex = "\\[(.*)\\] falls asleep".toRegex()
            val wakeRegex: Regex = "\\[(.*)\\] wakes up".toRegex()

            when {
                guardRegex.matches(line) -> {
                    val matchResult = guardRegex.find(line)
                    val (_, guardId) = matchResult!!.destructured
                    currentGuardId = guardId.toInt()
                }
                sleepRegex.matches(line) -> {
                    val matchResult = sleepRegex.find(line)
                    val (time) = matchResult!!.destructured
                    val datetime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    currentSleepMinute = datetime.minute
                }
                wakeRegex.matches(line) -> {
                    val matchResult = wakeRegex.find(line)
                    val (time) = matchResult!!.destructured
                    val datetime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    val wakeMinute = datetime.minute
                    val minuteRange: List<Int> = (currentSleepMinute until wakeMinute).toList()

                    guardSleeps[currentGuardId] = guardSleeps.getOrDefault(currentGuardId, emptyList()).plus(wakeMinute - currentSleepMinute)
                    guardMinutes[currentGuardId] = guardMinutes.getOrDefault(currentGuardId, emptyList()).plus(minuteRange)
                }
            }
        }
        val sleepyGuard = guardSleeps.map { Pair(it.key, it.value.sum()) }.sortedByDescending { it.second }.first()
        println("sleepyGuard pair = $sleepyGuard")
        println("sleepyGuard = ${sleepyGuard.first}")
        val result: Map<Int, Int> = guardMinutes[sleepyGuard.first]!!.groupingBy { it }.eachCount()
        val sleepyMinute = result.entries.sortedByDescending { it.value }.first().key
        println("sleepyMinute $sleepyMinute")
        println("PART ONE RESULT = ${sleepyGuard.first} * $sleepyMinute = " + sleepyGuard.first * sleepyMinute)

        println("************")
        val minutesToGuards = mutableMapOf<Int, List<Int>>()
        guardMinutes.entries.forEach { (id, minutes) ->
            run {
                minutes.forEach {
                    minutesToGuards.put(it, minutesToGuards.getOrDefault(it, emptyList()).plus(id))
                }
            }
        }
        var overallMax = Triple(0, 0, 0)
        minutesToGuards.forEach { (minute, guards) ->
            val guardToCount = guards.groupingBy { it }.eachCount()
            val maxBy: Map.Entry<Int, Int> = guardToCount.maxBy { it.value }!!
            if (maxBy.value > overallMax.third) {
                overallMax = Triple(minute, maxBy.key, maxBy.value)
            }
        }
        println("max tuple(minute, id, freq) = $overallMax")
        val (minute, id, _) = overallMax
        println("PART TWO RESULT = ${id * minute}")
    }

    @JvmStatic
    fun main(args: Array<String>) {

        val testInput = listOf(
            "[1518-11-01 00:00] Guard #10 begins shift",
            "[1518-11-01 00:05] falls asleep",
            "[1518-11-01 00:25] wakes up",
            "[1518-11-01 00:30] falls asleep",
            "[1518-11-01 00:55] wakes up",
            "[1518-11-01 23:58] Guard #99 begins shift",
            "[1518-11-02 00:40] falls asleep",
            "[1518-11-02 00:50] wakes up",
            "[1518-11-03 00:05] Guard #10 begins shift",
            "[1518-11-03 00:24] falls asleep",
            "[1518-11-03 00:29] wakes up",
            "[1518-11-04 00:02] Guard #99 begins shift",
            "[1518-11-04 00:36] falls asleep",
            "[1518-11-04 00:46] wakes up",
            "[1518-11-05 00:03] Guard #99 begins shift",
            "[1518-11-05 00:45] falls asleep",
            "[1518-11-05 00:55] wakes up"
        )

        Runner.timedRun(4) { input ->
            run(input)
        }
    }

}