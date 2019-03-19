package catalysts.training.deletable

import catalysts.level
import catalysts.output

private fun isPrime(n: Long): Boolean {
    if (n < 2) return false
    if (n == 2L || n == 3L) return true
    if (n % 2 == 0L || n % 3 == 0L) return false
    val sqrtN = Math.sqrt(n.toDouble()).toLong() + 1
    var i = 6L
    while (i <= sqrtN) {
        if (n % (i - 1) == 0L || n % (i + 1) == 0L) return false
        i += 6
    }
    return true
}

private fun test(number: String, excluded: MutableSet<Int>): Int {
    if (!isPrime(number.filterIndexed { i, c -> i !in excluded }.toLong())) return 0
    if (excluded.size == number.length) return 0
    if (excluded.size == number.length - 1) return 1
    var sum = 0
    for (i in 0 until number.length) {
        if (i !in excluded) {
            excluded.add(i)
            sum += test(number, excluded)
            excluded.remove(i)
        }
    }
    return sum
}

fun main() = level { input ->
    val number = input.next()
    val excluded = HashSet<Int>()
    val result = test(number, excluded)
    output(result)
}