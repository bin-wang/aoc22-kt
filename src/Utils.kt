import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

fun readInputAsString(name: String) = File("src", "$name.txt").readText()

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
    tailrec fun helper(res: List<List<T>>, current: List<T>, iterator: Iterator<T>): List<List<T>> {
        if (!iterator.hasNext()) {
            return res.plusElement(current)
        }
        val t = iterator.next();
        return if (predicate(t)) {
            helper(res.plusElement(current), listOf(), iterator)
        } else {
            helper(res, current.plusElement(t), iterator)
        }
    }

    return helper(listOf(), listOf(), this.iterator())
}
