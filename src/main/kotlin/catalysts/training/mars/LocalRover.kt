package catalysts.training.mars

import catalysts.Vector2

class LocalRover(val wheelBase: Double) {

    var position = Vector2(0.0, 0.0)
        private set

    private var _angle = 0.0

    val angle get() = Math.toDegrees(_angle).positiveAngle()

    fun turn(angle: Double, steeringAngle: Double) {
        val radius = getTurnRadius(wheelBase, steeringAngle)
        move(2 * Math.PI * radius * (angle / 360), steeringAngle)
    }

    fun move(distance: Double, steeringAngle: Double) {
        val radius = getTurnRadius(wheelBase, steeringAngle)
        val radians = distance / radius

        val change = if (steeringAngle != 0.0) {
            val x = radius - Math.cos(radians) * radius
            val y = Math.sin(radians) * radius

            Vector2(x, y)
        } else Vector2(0.0, distance)

        position += change.rotate(Math.toDegrees(_angle))

        this._angle += radians
    }

}