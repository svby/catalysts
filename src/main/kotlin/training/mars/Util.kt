package training.mars

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

fun getTurnRadius(wheelBase: Double, steeringAngle: Double): Double {
    return wheelBase / Math.sin(Math.toRadians(steeringAngle))
}

fun Double.format() = String.format("%.2f", this)

fun printf(format: String, vararg args: Any?) {
    System.out.printf(format, *args)
}

fun output(vararg values: Any) {
    val message = values.map {
        when (it) {
            is Double -> it.format()
            else -> it
        }
    }.joinToString(" ")

    clipboard(message)
    println(message)
}

fun Double.positiveAngle(): Double {
    var copy = this
    while (copy < 0) copy += 360
    while (copy > 360) copy -= 360
    return copy
}

fun clipboard(string: String) {
    val selection = StringSelection(string)
    val clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    clipboard.setContents(selection, selection)
}

fun MoveResult.requirePass() = this as? MoveResult.Pass ?: throw IllegalStateException("Objective not yet complete")
fun MoveResult.requireOk() = this as? MoveResult.Ok ?: throw IllegalStateException("Move did not succeed")