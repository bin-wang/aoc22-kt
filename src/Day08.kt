import kotlin.math.min

object Day08 {
    data class Tree(val x: Int, val y: Int)

    class Forest(private val heights: List<List<Int>>) {
        private val h = heights.size
        private val w = heights.first().size

        private fun leftward(tree: Tree) = ((tree.y - 1) downTo 0).map { Tree(tree.x, it) }
        private fun rightward(tree: Tree) = ((tree.y + 1) until w).map { Tree(tree.x, it) }
        private fun upward(tree: Tree) = ((tree.x - 1) downTo 0).map { Tree(it, tree.y) }
        private fun downward(tree: Tree) = ((tree.x + 1) until h).map { Tree(it, tree.y) }

        fun trees(): List<Tree> = (0 until h).cartesianProduct(0 until w).map { Tree(it.first, it.second) }

        fun visible(tree: Tree): Boolean {
            fun Tree.visibleInDirection(direction: (Tree) -> List<Tree>): Boolean {
                val myHeight = heights[x][y]
                return direction(this).map { heights[it.x][it.y] }.all { it < myHeight }
            }

            return tree.visibleInDirection(::leftward) ||
                    tree.visibleInDirection(::rightward) ||
                    tree.visibleInDirection(::upward) ||
                    tree.visibleInDirection(::downward)
        }

        fun score(tree: Tree): Int {
            fun Tree.scoreInDirection(direction: (Tree) -> List<Tree>): Int {
                val myHeight = heights[x][y]
                val otherTrees = direction(this)
                return min(otherTrees.takeWhile { heights[it.x][it.y] < myHeight }.size + 1, otherTrees.size)
            }
            return tree.scoreInDirection(::leftward) *
                    tree.scoreInDirection(::rightward) *
                    tree.scoreInDirection(::upward) *
                    tree.scoreInDirection(::downward)
        }
    }
}

fun main() {
    fun part1(forest: Day08.Forest): Int {
        return forest.trees().count { forest.visible(it) }
    }

    fun part2(forest: Day08.Forest): Int {
        return forest.trees().maxOf { forest.score(it) }
    }

    val testInput = readInput("Day08_test")
    val testHeights = testInput.map { line -> line.map { it.digitToInt() } }
    val testForest = Day08.Forest(testHeights)

    check(part1(testForest) == 21)
    check(part2(testForest) == 8)

    val input = readInput("Day08")
    val heights = input.map { line -> line.map { it.digitToInt() } }
    val forest = Day08.Forest(heights)

    println(part1(forest))
    println(part2(forest))
}
