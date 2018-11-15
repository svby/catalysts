package training.mars

import java.util.*

fun main(): Unit = Scanner(System.`in`).use { input ->
    val rover = LocalRover(input.nextDouble())

    repeat(input.nextInt()) {
        val distance = input.nextDouble()
        val steeringAngle = input.nextDouble()

        rover.move(distance, steeringAngle)
    }

    output(rover.position.x, rover.position.y, rover.angle.positiveAngle())
}
