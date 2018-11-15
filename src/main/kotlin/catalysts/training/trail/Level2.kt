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

    output(jim.distance, (maxX - minX) * (maxY - minY))
}