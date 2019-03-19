package catalysts.training.flow

import catalysts.Vector2
import catalysts.Vector2L
import catalysts.level
import catalysts.output
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import java.awt.*
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

private fun getRow(value: Int, columns: Int) = (value - 1) / columns
private fun getColumn(value: Int, columns: Int) = (value - 1) % columns

private enum class Step4(val rowDelta: Int, val columnDelta: Int) {

    N(-1, 0) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getRow(point, columns) - 1 in 0 until rows
        override fun perform(point: Int, rows: Int, columns: Int) = point - columns
    },
    E(0, 1) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getColumn(point, columns) + 1 in 0 until columns
        override fun perform(point: Int, rows: Int, columns: Int) = point + 1
    },
    S(1, 0) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getRow(point, columns) + 1 in 0 until rows
        override fun perform(point: Int, rows: Int, columns: Int) = point + columns
    },
    W(0, -1) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getColumn(point, columns) - 1 in 0 until columns
        override fun perform(point: Int, rows: Int, columns: Int) = point - 1
    };

    abstract fun isValid(point: Int, rows: Int, columns: Int): Boolean
    abstract fun perform(point: Int, rows: Int, columns: Int): Int

}

fun main(): Unit = level { input ->
    val rows = input.nextInt()
    val columns = input.nextInt()

    val n = input.nextInt()
    val grid = IntArray(rows * columns + 1)

    for (i in 0 until n) grid[input.nextInt()] = input.nextInt()

    val np = input.nextInt()

    val frame = JFrame("CCC 2014")
    frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE

    for (p in 0 until np) {
        val color = input.nextInt()
        val start = input.nextInt()
        val ns = input.nextInt()

        var position = start
        var ok = true

        val steps = (0 until ns).map { Step4.valueOf(input.next()) }
        val visited = ArrayList<Int>(ns)
        for (s in 0 until ns) {
            val step = steps[s]

            if (!step.isValid(position, rows, columns)) {
                ok = false
                break
            }

            position = step.perform(position, rows, columns)

            if (grid[position].let { it != 0 && it != color }) {
                ok = false
                break
            }

            visited.add(position)
        }

        if (ok && (position == start || grid[position] != color)) {
            ok = false
        }

        if (ok) {
            grid[position] = color

            // Show the path
            for (point in visited) grid[point] = -color
        }
    }

    val scale = 1
    val canvas = object : JPanel() {
        override fun paintComponent(g: Graphics) {
            for (point in 1 until grid.size) {
                val color = grid[point]
                if (color != 0) {
                    g.fillRect((getColumn(point, columns) * scale), (getRow(point, columns) * scale), scale, scale)
                }
            }
        }
    }

    canvas.preferredSize = Dimension(columns * scale, rows * scale)
    frame.add(canvas)
    frame.pack()
    frame.isVisible = true
}