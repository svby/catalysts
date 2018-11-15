package catalysts.training.mars

import catalysts.output

fun main() {
    val username = System.getProperty("catcoder.username")
    if (username == null) {
        println("No user specified, exiting")
        return
    }
    println("Logged in as $username")

    val rover = RemoteRover.create("L4_MFJS3487", true) ?: return
    println("Created rover $rover")

    println(rover.move(100.0, 0.0))

    // Turn 270 degrees CW
    run {
        val angle = rover.maxSteerAngle
        val radius = rover.getTurnRadius(angle)

        val distance = 1.5 * Math.PI * radius
        rover.move(distance, angle).requireOk()
    }

    // Turn 90 degrees CCW
    val remaining = run {
        val angle = -rover.maxSteerAngle
        val radius = rover.getTurnRadius(angle)

        val distance = -0.5 * Math.PI * radius
        val result = rover.move(distance, angle).requireOk()

        result.position!!.y
    }

    val result = rover.move(remaining, 0.0).requirePass()

    output(result.key)
}
