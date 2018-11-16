package catalysts.school.ccc29

import catalysts.output
import java.util.*

fun main(): Unit = Scanner(System.`in`).use { input ->
    val rows = input.nextInt()
    val columns = input.nextInt()

    val map = Array(rows) { IntArray((columns)) }

    outer@ for (row in 0 until rows) {
        for (column in 0 until columns) {
            val height = input.nextInt()
            map[row][column] = height

            if (height != 0) {
                output(1)
                return
            }
        }
    }

    output(0)
}