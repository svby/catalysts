package catalysts

import kotlin.math.cos
import kotlin.math.sin

data class Vector2(val x: Double, val y: Double) {

    companion object {

        @JvmStatic
        val X = Vector2(1.0, 0.0)

        @JvmStatic
        val Y = Vector2(0.0, 1.0)

        @JvmStatic
        val ZERO = Vector2(0.0, 0.0)

    }

    override fun toString() = "($x, $y)"

    operator fun plus(other: Vector2) = Vector2(x + other.x, y + other.y)
    operator fun minus(other: Vector2) = Vector2(x - other.x, y - other.y)
    operator fun times(scalar: Byte) = Vector2(x * scalar, y * scalar)
    operator fun times(scalar: Short) = Vector2(x * scalar, y * scalar)
    operator fun times(scalar: Int) = Vector2(x * scalar, y * scalar)
    operator fun times(scalar: Long) = Vector2(x * scalar, y * scalar)
    operator fun times(scalar: Float) = Vector2(x * scalar, y * scalar)
    operator fun times(scalar: Double) = Vector2(x * scalar, y * scalar)
    operator fun times(other: Vector2) = x * other.x + y * other.y
    operator fun div(scalar: Byte) = Vector2(x / scalar, y / scalar)
    operator fun div(scalar: Short) = Vector2(x / scalar, y / scalar)
    operator fun div(scalar: Int) = Vector2(x / scalar, y / scalar)
    operator fun div(scalar: Long) = Vector2(x / scalar, y / scalar)
    operator fun div(scalar: Float) = Vector2(x / scalar, y / scalar)
    operator fun div(scalar: Double) = Vector2(x / scalar, y / scalar)
    operator fun unaryMinus() = Vector2(-x, -y)
    operator fun unaryPlus() = this

    val length: Double
        get() {
            return Math.sqrt(x * x + y * y)
        }

    val angle: Double
        get() {
            return Math.toDegrees(Math.atan2(x, y))
        }

    fun rotate(angle: Double) = Math.toRadians(angle).let { rad ->
        Vector2(
                cos(rad) * x + sin(rad) * y,
                -sin(rad) * x + cos(rad) * y
        )
    }

    fun toVector3(z: Double = 0.0) = Vector3(x, y, z)

}