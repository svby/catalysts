package catalysts.training.mars

import catalysts.Vector2

class RemoteRover(
    val uuid: String,
    val managed: Boolean,

    val wheelBase: Double,
    val maxSteerAngle: Double,

    val target: Vector2,
    val targetRadius: Double
) {

    companion object {

        @JvmStatic
        fun create(
            map: String,
            managed: Boolean = false,
            username: String = System.getProperty("catcoder.username"),
            contestId: String = "practice"
        ): RemoteRover? {
            return RoverRest.retrieveRover(RoverRest.createRover(map, username, contestId) ?: return null, managed)
        }

    }

    var position = Vector2.ZERO
        private set

    private var _angle = 0.0

    val angle get() = _angle.positiveAngle()

    override fun toString() =
        "RemoteRover(uuid='$uuid', wheelBase=$wheelBase, maxSteerAngle=$maxSteerAngle, target=$target, targetRadius=$targetRadius)"

    fun getTurnRadius(steeringAngle: Double) = catalysts.training.mars.getTurnRadius(wheelBase, steeringAngle)

    fun turn(angle: Double, steeringAngle: Double): MoveResult {
        val radius = getTurnRadius(steeringAngle)
        return move(2 * Math.PI * radius * (angle / 360), steeringAngle)
    }

    fun move(distance: Double, steeringAngle: Double): MoveResult {
        val move = RoverRest.moveRover(uuid, distance, steeringAngle)

        if (managed && move is MoveResult.Ok) {
            move.position?.let { position = move.position }
            move.angle?.let { _angle = move.angle }
        } else {
            val radius = getTurnRadius(wheelBase, steeringAngle)
            val radians = distance / radius

            val change = if (steeringAngle != 0.0) {
                val x = radius - Math.cos(radians) * radius
                val y = Math.sin(radians) * radius

                Vector2(x, y)
            } else Vector2(0.0, distance)

            position += change.rotate(_angle)

            this._angle += Math.toDegrees(radians)
        }

        return move
    }

}