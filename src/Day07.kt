object Day07 {
    data class File(val name: String, val size: Int)

    class Directory(val name: String, val parent: Directory?) {
        var dirs: List<Directory> = listOf()
        var files: List<File> = listOf()
        val size: Int by lazy {
            files.sumOf { it.size } + dirs.sumOf { it.size }
        }

        fun traverse(): List<Directory> {
            return listOf(this) + dirs.flatMap { it.traverse() }
        }
    }

    sealed interface Command {
        companion object {
            fun from(input: String): Command {
                val tokens = input.split(' ').filter(String::isNotBlank)
                return when (tokens[0]) {
                    "cd" -> CD(directoryName = tokens[1])
                    "ls" -> LS
                    else -> error("Unable to parse command")
                }
            }
        }
    }

    data class CD(val directoryName: String) : Command
    object LS : Command

    const val total = 70000000
    const val needed = 30000000
}

fun main() {
    fun parseInputAsFilesystemTree(input: String): Day07.Directory {
        val snippets = input.split("$").filter(String::isNotBlank).map { it.trim().lines() }
        val root = Day07.Directory("/", parent = null)
        var currentDir = root

        snippets.forEach { s ->
            when (val command = Day07.Command.from(s.first())) {
                Day07.LS -> {
                    val (dirTokens, fileTokens) = s.drop(1).partition { it.startsWith("dir") }

                    currentDir.dirs = dirTokens.map {
                        val (_, dirName) = it.split(' ')
                        Day07.Directory(dirName, parent = currentDir)
                    }

                    currentDir.files = fileTokens.map {
                        val (size, fileName) = it.split(' ')
                        Day07.File(fileName, size.toInt())
                    }
                }

                is Day07.CD -> {
                    currentDir = when (command.directoryName) {
                        "/" -> root
                        ".." -> currentDir.parent!!
                        else -> currentDir.dirs.first { it.name == command.directoryName }
                    }
                }
            }
        }
        return root
    }

    fun part1(root: Day07.Directory) =
        root.traverse().mapNotNull { directory -> directory.size.takeIf { it <= 100000 } }.sum()

    fun part2(root: Day07.Directory): Int {
        val free = Day07.total - root.size
        val toFree = Day07.needed - free

        return root.traverse().mapNotNull { directory -> directory.size.takeIf { it >= toFree } }.min()
    }

    val testInput = readInputAsString("Day07_test")
    val testRoot = parseInputAsFilesystemTree(testInput)

    check(part1(testRoot) == 95437)
    check(part2(testRoot) == 24933642)

    val input = readInputAsString("Day07")
    val root = parseInputAsFilesystemTree(input)

    println(part1(root))
    println(part2(root))

}
