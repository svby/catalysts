package catalysts.training.flow

import catalysts.Vector2
import catalysts.Vector2L
import catalysts.level
import catalysts.output
import java.util.*

private data class Point2(
        val position: Vector2L,
        val color: Int
)

fun main(): Unit = level { input ->
    val rows = input.nextInt()
    val columns = input.nextInt()

    val n = input.nextInt()
    val points = ArrayList<Point2>()

    for (i in 0 until n) {
        val p = input.nextInt()
        val position = Vector2L(((p - 1) / columns).toLong(), ((p - 1) % columns).toLong())
        points.add(Point2(position, input.nextInt()))
    }

    val result = (1..n / 2).map { color ->
        points.first { it.color == color }.position.manhattan(points.last { it.color == color }.position)
    }
    output(result.joinToString(" "))
}