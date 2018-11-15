package catalysts.training.trail

import catalysts.output
import java.util.*

fun main(): Unit = Scanner(System.`in`).let { input ->
    val jim = Jim()

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
            }
        }
    }

    output(jim.distance)
}