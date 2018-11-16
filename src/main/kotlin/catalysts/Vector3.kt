package catalysts

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
    operator fun times(scalar: Byte) = Vector3(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Short) = Vector3(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Int) = Vector3(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Long) = Vector3(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Float) = Vector3(x * scalar, y * scalar, z * scalar)
    operator fun times(scalar: Double) = Vector3(x * scalar, y * scalar, z * scalar)
    operator fun div(scalar: Byte) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Short) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Int) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Long) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Float) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun div(scalar: Double) = Vector3(x / scalar, y / scalar, z / scalar)
    operator fun unaryMinus() = Vector3(-x, -y, -z)
    operator fun unaryPlus() = this

    val xy: Vector2 get() = Vector2(x, y)

    val length: Double get() = Math.sqrt(x * x + y * y + z * z)

}