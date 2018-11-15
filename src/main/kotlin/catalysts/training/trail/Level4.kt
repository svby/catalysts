package catalysts.training.trail

import catalysts.output
import java.util.*
import kotlin.math.max
import kotlin.math.min

fun main(): Unit = Scanner(System.`in`).let { input ->
    val jim = Jim()

    var minX = Integer.MAX_VALUE
    var maxX = Integer.MIN_VALUE
    var minY = Integer.MAX_VALUE
    var maxY = Integer.MIN_VALUE

    repeat(input.nextInt()) {
        val move = input.next()
        val count = input.nextInt()

        repeat(count) {
            for (char in move) {
                when (char) {
                    'F' -> jim.move(1)
                    'R' -> jim.turnRight(1)
                    'L' -> jim.turnLeft(1)
                }
                minX = min(minX, jim.position.x.toInt())
                minY = min(minY, jim.position.y.toInt())
                maxX = max(maxX, jim.position.x.toInt())
                maxY = max(maxY, jim.position.y.toInt())
            }
        }
    }

    jim.stop()

    val vertices = jim.vertices
    val area = polyArea(vertices)

    var pocketArea = 0

    fun close(a: Double, b: Double) = Math.abs(a - b) <= 0.1

    for (x in minX until maxX) for (y in minY until maxY) {
        val pX = x + 0.5
        val pY = y + 0.5

        // Perform raycast
        val intersections = vertices.count {
            it.x > pX && close(it.y, pY)
        }

        // This is inside the polygon
        if (intersections % 2 == 1) continue

        val horizCheck =
            vertices.any { close(it.y, pY) && it.x < pX } && vertices.any { close(it.y, pY) && it.x > pX }
        val vertCheck =
            vertices.any { close(it.x, pX) && it.y < pY } && vertices.any { close(it.x, pX) && it.y > pY }

        if (horizCheck || vertCheck) {
            println("$pX $pY")
            pocketArea++
        }
    }

    output(jim.distance, (maxX - minX) * (maxY - minY), area.toInt(), pocketArea)
}