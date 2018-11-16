package catalysts

import kotlin.math.cos
import kotlin.math.sin

data class Vector3(val x: Double, val y: Double, val z: Double) {

    companion object {

        @JvmStatic
        val X = Vector3(1.0, 0.0, 0.0)

        @JvmStatic
        val Y = Vector3(0.0, 1.0, 0.0)

        @JvmStatic
        val Z = Vector3(0.0, 0.0, 1.0)

        @JvmStatic
        val ZERO = Vector3(0.0, 0.0, 0.0)


    }

    override fun toString() = "($x, $y, $z)"

    operator fun plus(other: Vector3) = Vector3(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Vector3) = Vector3(x - other.x, y - other.y, z - other.z)
    operator fun div(scalar: Double) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Int) = Vector3(x / scalar, y / scalar, z / scalar)

    val xy: Vector2
        get() {
            return Vector2(x, y)
        }

    val length: Double
        get() {
            return Math.sqrt(x * x + y * y + z * z)
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


}