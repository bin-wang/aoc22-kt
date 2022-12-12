object Day11 {

}

fun main() {
    data class Monkey(
        val items: MutableList<Long>,
        val operation: (Long) -> Long,
        val divisor: Int,
        val trueMonkeyIndex: Int,
        val falseMonkeyIndex: Int
    ) {
        fun testAndThrow(item: Long) = if (item % divisor == 0L) trueMonkeyIndex else falseMonkeyIndex
    }

    fun parseMonkey(description: List<String>): Monkey {
        val items = description[1].split(":")[1].split(",").map { it.trim().toLong() }
        val (operator, other) = description[2].split(" ").takeLast(2)
        val operation = when (operator) {
            "+" -> { old: Long -> StrictMath.addExact(old, other.toLong()) }
            "*" -> if (other == "old") { old: Long ->
                StrictMath.multiplyExact(old, old)
            } else { old: Long ->
                StrictMath.multiplyExact(old, other.toInt())
            }

            else -> error("Unexpected operator")
        }
        val (divisor, ifTrue, ifFalse) = description.takeLast(3).map { it.split(" ").last().toInt() }
        return Monkey(items.toMutableList(), operation, divisor, ifTrue, ifFalse)
    }

    fun run(monkeys: List<Monkey>, worryCopingMethod: (Long) -> Long, repetitions: Int): Map<Int, Int> {
        val business = mutableMapOf<Int, Int>()
        repeat(repetitions) {
            monkeys.forEachIndexed { i, monkey ->
                business[i] = business.getOrDefault(i, 0) + monkey.items.size
                val itemsToThrowByMonkeyIndex: Map<Int, List<Long>> =
                    monkey
                        .items
                        .map { worry -> worry.run(monkey.operation).run(worryCopingMethod) }
                        .groupBy(monkey::testAndThrow)
                monkey.items.clear()
                itemsToThrowByMonkeyIndex.forEach { (monkeyIndex, itemsToThrow) ->
                    monkeys[monkeyIndex].items.addAll(itemsToThrow)
                }
            }
        }
        return business
    }

    fun part1(input: String): Long {
        val monkeys = input.split("\n\n").map { parseMonkey(it.trim().lines()) }
        val business = run(monkeys, worryCopingMethod = { x -> x / 3 }, 20)
        return business.values.sortedDescending().take(2).fold(1L) { acc, i -> acc * i }
    }

    fun part2(input: String): Long {
        val monkeys = input.split("\n\n").map { parseMonkey(it.trim().lines()) }
        val commonMultiplier = monkeys.map  { it.divisor }.fold(1) {acc, i -> acc * i }
        val business = run(monkeys, worryCopingMethod = { x -> x % commonMultiplier }, 10000)
        return business.values.sortedDescending().take(2).fold(1L) { acc, i -> acc * i }
    }

    val testInput = readInputAsString("Day11_test")
    val input = readInputAsString("Day11")

    println(part1(testInput))
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}
