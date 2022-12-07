fun main() {
    // Returns the number of characters needed to be processed before the first start-of-packet marker is detected
    fun locateMarker(packet: String, windowSize: Int) =
        packet.windowed(windowSize).indexOfFirst { it.toSet().size == windowSize } + windowSize

    fun part1(input: String) = locateMarker(input, windowSize = 4)

    fun part2(input: String) = locateMarker(input, windowSize = 14)

    val testInput = readInputAsString("Day06_test").trim()
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInputAsString("Day06").trim()
    println(part1(input))
    println(part2(input))
}
