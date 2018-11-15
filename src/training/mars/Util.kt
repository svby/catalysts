package training.mars

fun getTurnRadius(wheelBase: Double, steeringAngle: Double): Double {
    return wheelBase / Math.sin(Math.toRadians(steeringAngle))
}

fun Double.format() = String.format("%.2f", this)

fun printf(format: String, vararg args: Any?) {
    System.out.printf(format, *args)
}
