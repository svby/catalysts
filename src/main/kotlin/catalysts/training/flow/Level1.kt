package catalysts.training.flow

import catalysts.level
import catalysts.output
import java.util.*

fun main(): Unit = level { input ->
    val rows = input.nextInt()
    val columns = input.nextInt()

    val n = input.nextInt()
    val result = ArrayList<Int>()
    for (i in 0 until n) {
        val p = input.nextInt()
        result.add((p - 1) / columns + 1)
        result.add((p - 1) % columns + 1)
    }

    output(result.joinToString(" "))
}