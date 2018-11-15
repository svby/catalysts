package catalysts.training.mars

fun getTurnRadius(wheelBase: Double, steeringAngle: Double): Double {
    return wheelBase / Math.sin(Math.toRadians(steeringAngle))
}

fun Double.format() = String.format("%.2f", this)

fun Double.positiveAngle(): Double {
    var copy = this
    while (copy < 0) copy += 360
    while (copy > 360) copy -= 360
    return copy
}

fun MoveResult.requirePass() = this as? MoveResult.Pass ?: throw IllegalStateException("Objective not yet complete")
fun MoveResult.requireOk() = this as? MoveResult.Ok ?: throw IllegalStateException("Move did not succeed")