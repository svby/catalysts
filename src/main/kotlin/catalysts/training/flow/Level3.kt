package catalysts.training.flow

import catalysts.Vector2
import catalysts.Vector2L
import catalysts.level
import catalysts.output
import java.util.*
import kotlin.collections.ArrayList

enum class Step3(val rowDelta: Int, val columnDelta: Int) {

    N(-1, 0),
    E(0, 1),
    S(1, 0),
    W(0, -1);

    val vector = Vector2L(rowDelta.toLong(), columnDelta.toLong())

}

private fun toVector2L(rows: Int, columns: Int, position: Int) =
        Vector2L(((position - 1) / columns).toLong(), ((position - 1) % columns).toLong())

private data class Point3(
        val position: Vector2L,
        val color: Int
) {

    constructor(rows: Int, columns: Int, position: Int, color: Int)
            : this(toVector2L(rows, columns, position), color)

}

fun main(): Unit = level { input ->
    val rows = input.nextInt()
    val columns = input.nextInt()

    val n = input.nextInt()
    val points = ArrayList<Point3>()

    for (i in 0 until n) points.add(Point3(rows, columns, input.nextInt(), input.nextInt()))

    val np = input.nextInt()
    val result = ArrayList<Int>()
    for (p in 0 until np) {
        val color = input.nextInt()
        val start = toVector2L(rows, columns, input.nextInt())
        val ns = input.nextInt()
        var position = start

        val seen = HashSet<Vector2L>()
        var ok = true
        var fi = ns
        for (s in 0 until ns) {
            val step = Step3.valueOf(input.next())
            position += step.vector

            if (position in seen
                    || position.x !in 0 until rows
                    || position.y !in 0 until columns
                    || points.any { it.position == position && it.color != color }) {
                ok = false
                fi = s + 1
                break
            }

            seen.add(position)
        }

        if (ok && (position == start || points.none { it.position == position && it.color == color })) {
            ok = false
            fi = ns
        }

        result.add(if (ok) 1 else -1)
        result.add(fi)
    }

    output(result.joinToString(" "))
}