package catalysts.training.flow

import catalysts.level
import catalysts.output
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.HashSet

private fun getRow(value: Int, columns: Int) = (value - 1) / columns
private fun getColumn(value: Int, columns: Int) = (value - 1) % columns

private enum class Step5(val rowDelta: Int, val columnDelta: Int) {

    N(-1, 0) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getRow(point, columns) - 1 in 0 until rows
        override fun perform(point: Int, rows: Int, columns: Int) = point - columns
    },
    E(0, 1) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getColumn(point, columns) + 1 in 0 until columns
        override fun perform(point: Int, rows: Int, columns: Int) = point + 1
    },
    S(1, 0) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getRow(point, columns) + 1 in 0 until rows
        override fun perform(point: Int, rows: Int, columns: Int) = point + columns
    },
    W(0, -1) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getColumn(point, columns) - 1 in 0 until columns
        override fun perform(point: Int, rows: Int, columns: Int) = point - 1
    };

    abstract fun isValid(point: Int, rows: Int, columns: Int): Boolean
    abstract fun perform(point: Int, rows: Int, columns: Int): Int

}

fun main(): Unit = level { input ->
    val nt = input.nextInt()
    val results = StringBuilder()

    for (t in 0 until nt) {
        val rows = input.nextInt()
        val columns = input.nextInt()
        val n = input.nextInt()
        val grid = IntArray(rows * columns + 1)
        val colors = IntArray(n + 2)

        val result = IntArray(n / 2)

        for (i in 0 until n) {
            val point = input.nextInt()
            val color = input.nextInt()
            grid[point] = color

            if (colors[color * 2] != 0) colors[color * 2 + 1] = point
            else colors[color * 2] = point
        }

        val np = input.nextInt()

        for (p in 0 until np) {
            val color = input.nextInt()
            val start = input.nextInt()
            val ns = input.nextInt()

            var position = start

            val steps = (0 until ns).map { Step5.valueOf(input.next()) }

            for (s in 0 until ns) {
                val step = steps[s]
                position = step.perform(position, rows, columns)
                grid[position] = -color
            }
            grid[position] = color
            result[color-1] = 1
        }

        for (color in 1..n / 2) {
            if (result[color-1] != 0) continue
            println("Processing $color")

            val start = colors[color * 2]
            val end = colors[color * 2 + 1]

            // Try to find path (DFS)
            val q = ArrayDeque<Int>()
            val seen = HashSet<Int>()

            q.add(start)
            seen.add(start)

            var ok = false
            while (q.isNotEmpty()) {
                val next = q.removeLast()

                if (next == end) {
                    result[color - 1] = 2
                    ok = true
                    break
                }

                for (step in Step5.values()) {
                    if (!step.isValid(next, rows, columns)) continue
                    val child = step.perform(next, rows, columns)
                    if (child in seen) continue

                    val occupant = grid[child]
                    if (occupant == color || occupant == 0) {
                        seen.add(next)
                        q.add(child)
                    }
                }
            }

            if (!ok) result[color - 1] = 3
        }

        results.append(result.joinToString(" ")).append('\n')
    }
    output(results)
}