package training.mars

class LocalRover(val wheelBase: Double) {

    var position = Vector2(0.0, 0.0)
        private set

    private var _angle = 0.0

    val angle get() = Math.toDegrees(_angle)

    fun turn(distance: Double, angle: Double) {
        val radius = getTurnRadius(wheelBase, angle)
        val radians = distance / radius

        val change = if (angle != 0.0) {
            val x = radius - Math.cos(radians) * radius
            val y = Math.sin(radians) * radius

            Vector2(x, y)
        } else Vector2(0.0, distance)

        position += change.rotate(Math.toDegrees(_angle))

        this._angle += radians
    }

}