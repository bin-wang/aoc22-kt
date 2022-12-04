fun main() {
    fun IntRange.fullyContains(other: IntRange) = first <= other.first && last >= other.last

    fun IntRange.overlaps(other: IntRange) = !(last < other.first || first > other.last)

    fun parseLine(line: String): List<IntRange> {
        return line.split(",").map {
            val (first, last) = it.split("-")
            first.toInt()..last.toInt()
        }
    }

    fun part1(input: List<String>): Int {
        return input.map(::parseLine).count { (r1, r2) ->
            r1.fullyContains(r2) || r2.fullyContains(r1)
        }
    }

    fun part2(input: List<String>): Int {
        return input.map(::parseLine).count { (r1, r2) ->
            r1.overlaps(r2)
        }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
