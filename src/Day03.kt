fun main() {
    fun Char.toPriority() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("unexpected input: $this")
    }

    fun part1(input: List<String>): Int {
        return input.sumOf {
            val item1 = it.slice(0 until it.length / 2)
            val item2 = it.slice(it.length / 2 until it.length)
            val commonItem = item1.toSet().intersect(item2.toSet()).first().toChar()
            commonItem.toPriority()
        }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).sumOf { elves ->
            elves.map { it.toSet() }.reduce(Set<Char>::intersect).first().toPriority()
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
