package catalysts.training.mars

import catalysts.output
import java.util.*

fun main(): Unit = Scanner(System.`in`).use { input ->
    val wheelBase = input.nextDouble()
    val steeringAngle = input.nextDouble()

    output(getTurnRadius(wheelBase, steeringAngle).format())
}
