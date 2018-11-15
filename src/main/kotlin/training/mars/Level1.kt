package training.mars

import java.util.*

fun main(): Unit = Scanner(System.`in`).use { input ->
    val wheelBase = input.nextDouble()
    val steeringAngle = input.nextDouble()

    println(getTurnRadius(wheelBase, steeringAngle).format())
}
