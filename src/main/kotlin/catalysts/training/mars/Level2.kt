package catalysts.training.mars

import catalysts.output
import java.util.*

fun main(): Unit = Scanner(System.`in`).use { input ->
    val rover = LocalRover(input.nextDouble())

    val distance = input.nextDouble()
    val steeringAngle = input.nextDouble()

    rover.move(distance, steeringAngle)

    output(rover.position.x, rover.position.y, rover.angle.positiveAngle())
}
