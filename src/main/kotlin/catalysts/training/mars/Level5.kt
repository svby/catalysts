package catalysts.training.mars

import catalysts.output

private const val epsilon = 0.001

fun main() {
    val username = System.getProperty("catcoder.username")
    if (username == null) {
        println("No user specified, exiting")
        return
    }
    println("Logged in as $username")

    val rover = RemoteRover.create("L5_MAF3401R") ?: return
    println("Created rover $rover")

    val target = rover.target

    val angle = rover.maxSteerAngle
    val radius = rover.getTurnRadius(angle)

    var distance = 0.0

    while (true) {
        val localRover = LocalRover(rover.wheelBase)
        distance += 0.00001
        localRover.turn(distance, angle)

        val angleToTarget = (target - localRover.position).angle

        if (Math.abs(angleToTarget - localRover.angle) <= 0.001) {
            break
        }
    }

    println(rover.turn(distance, angle).requireOk())

    val res = rover.move((target - rover.position).length, 0.0).requirePass()

    output(res.key)
}
