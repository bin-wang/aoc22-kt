import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Split Iterable into chunks by predicate
 */
fun <T> Iterable<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    tailrec fun helper(res: MutableList<List<T>>, current: MutableList<T>, iterator: Iterator<T>): List<List<T>> {
        if (!iterator.hasNext()) {
            res.add(current.toList())
            return res.toList()
        }
        val x = iterator.next();
        return if (predicate(x)) {
            res.add(current.toList())
            helper(res, mutableListOf(), iterator)
        } else {
            current.add(x)
            helper(res, current, iterator)
        }
    }

    return helper(mutableListOf(), mutableListOf(), this.iterator())
}
