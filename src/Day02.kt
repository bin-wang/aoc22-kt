fun main() {
    fun part1(input: List<String>) =
        input
            .map { it[0] - 'A' to it[2] - 'X' }
            .sumOf { (opponent, mine) -> 1 + mine + 3 * (mine - opponent + 1).mod(3) }


    fun part2(input: List<String>) =
        input
            .map { it[0] - 'A' to it[2] - 'X' }
            .sumOf { (opponent, result) -> 1 + (opponent + result - 1).mod(3) + 3 * result }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
