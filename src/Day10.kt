import kotlin.math.abs

fun main() {
    data class State(val register: Int, val history: List<Int>)

    fun solve(input: List<String>): Int {

        val finalState = input.fold(State(1, listOf())) { acc, line ->
            when (line) {
                "noop" -> State(acc.register, acc.history + acc.register)
                else -> {
                    val increment = line.split(" ")[1].toInt()
                    State(acc.register + increment, acc.history + List(2) { acc.register })
                }
            }
        }

        // visualization for part 2
        finalState.history.chunked(40).forEach {
            val line = it.mapIndexed { index, i ->
                if (abs(index - i) <= 1) {
                    '█'
                } else {
                    ' '
                }
            }.joinToString(separator = "")
            println(line)
        }

        // return answer to part 1
        return finalState.history.mapIndexedNotNull { index, v ->
            when {
                (index + 1 - 20) % 40 == 0 -> (index + 1) * v
                else -> null
            }
        }.sum()
    }

    val testInput = readInput("Day10_test")
    solve(testInput)

    val input = readInput("Day10")
    solve(input)
}
