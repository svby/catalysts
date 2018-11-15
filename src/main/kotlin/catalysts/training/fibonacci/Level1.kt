package catalysts.training.fibonacci

import catalysts.output
import java.util.*

fun fibonacci(index: Int): Long {
    var last2 = 0L
    var last1 = 1L
    var value: Long

    for (i in 0 until index - 2) {
        value = last2 + last1

        last2 = last1
        last1 = value
    }

    return last2 + last1
}

fun main() = output(fibonacci(Scanner(System.`in`).use { it.nextInt() }))
