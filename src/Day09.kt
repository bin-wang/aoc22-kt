import kotlin.math.abs
import kotlin.math.sign

fun main() {
    data class Point(val x: Int, val y: Int) {
        fun move(direction: String) = when (direction) {
            "U" -> Point(x, y + 1)
            "D" -> Point(x, y - 1)
            "L" -> Point(x - 1, y)
            "R" -> Point(x + 1, y)
            else -> error("Unexpected direction")
        }

        fun follow(other: Point) =
            if (abs(other.x - x) <= 1 && abs(other.y - y) <= 1) {
                this
            } else {
                Point(x + (other.x - x).sign, y + (other.y - y).sign)
            }
    }

    fun part1(input: List<String>): Int {
        val directions = input.flatMap {
            val (direction, n) = it.split(" ")
            List(n.toInt()) { direction }
        }
        val tailHistory = directions
            .asSequence()
            .scan(Pair(Point(0, 0), Point(0, 0))) { rope: Pair<Point, Point>, direction: String ->
                val (head, tail) = rope
                val newHead = head.move(direction)
                val newTail = tail.follow(head)
                Pair(newHead, newTail)
            }
            .map { it.second }
            .toSet()
        return tailHistory.size
    }

    fun part2(input: List<String>): Int {
        val directions = input.flatMap { line ->
            val (direction, n) = line.split(" ")
            List(n.toInt()) { direction }
        }
        val tailHistory = directions
            .asSequence()
            .scan(List(10) { Point(0, 0) }) { rope: List<Point>, direction: String ->
                val newHead = rope.first().move(direction)
                rope.drop(1).scan(newHead) { prev: Point, point: Point ->
                    point.follow(prev)
                }
            }
            .map { it.last() }
            .toSet()
        return tailHistory.size
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
