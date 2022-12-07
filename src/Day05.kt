object Day05 {
    class State(val stacks: List<List<Char>>) {
        companion object {
            fun fromString(input: String): State {
                val lines = input.lines()

                // Get the number of stacks
                val n = lines
                    .last()
                    .split(" ")
                    .last(String::isNotEmpty)
                    .toInt()

                // parse each stack
                val stacks = (0 until n).map { i ->
                    lines
                        .dropLast(1)
                        .map { it.padEnd(4 * n + 3).chunked(4)[i][1] }
                        .takeLastWhile(Char::isLetter)
                }

                return State(stacks)
            }
        }

        fun move(step: Step, chunked: Boolean): State {
            val (i, from, to) = step
            val cratesToMove = stacks[from - 1].take(i).let { if (chunked) it else it.reversed() }
            val newStacks = stacks.mapIndexed { index, stack ->
                when (index) {
                    from - 1 -> stack.drop(i)
                    to - 1 -> cratesToMove + stack
                    else -> stack
                }
            }
            return State(newStacks)
        }

        fun getTopCratesAsString(): String =
            stacks
                .mapNotNull { it.firstOrNull() }
                .joinToString(separator = "")
    }

    data class Step(val i: Int, val from: Int, val to: Int)
}

fun main() {
    fun parseSteps(steps: List<String>) = steps.map {
        val chunks = it.split(" ")
        val i = chunks[1].toInt()
        val from = chunks[3].toInt()
        val to = chunks[5].toInt()
        Day05.Step(i, from, to)
    }

    fun part1(input: String): String {
        val (initialStateStr, stepsStr) = input.split("\n\n")
        val steps = parseSteps(stepsStr.trim().lines())
        return steps.fold(Day05.State.fromString(initialStateStr)) { state, step ->
            state.move(step, chunked = false)
        }.getTopCratesAsString()
    }

    fun part2(input: String): String {
        val (initialStateStr, stepsStr) = input.split("\n\n")
        val steps = parseSteps(stepsStr.trim().lines())
        return steps.fold(Day05.State.fromString(initialStateStr)) { state, step ->
            state.move(step, chunked = true)
        }.getTopCratesAsString()
    }

    val testInput = readInputAsString("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInputAsString("Day05")
    println(part1(input))
    println(part2(input))
}
