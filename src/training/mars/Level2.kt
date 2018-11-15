package training.mars

import java.util.*

fun main(): Unit = Scanner(System.`in`).use { input ->
    val rover = Rover(input.nextDouble())

    val distance = input.nextDouble()
    val steeringAngle = input.nextDouble()

    rover.turn(distance, steeringAngle)

    output(rover.position.x, rover.position.y, rover.angle.positiveAngle())
}