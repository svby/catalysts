package catalysts.training.flow

import catalysts.level
import catalysts.output
import java.lang.StringBuilder
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet
import kotlin.system.exitProcess

private fun getRow(value: Int, columns: Int) = (value - 1) / columns
private fun getColumn(value: Int, columns: Int) = (value - 1) % columns

private enum class Step6(val rowDelta: Int, val columnDelta: Int) {

    N(-1, 0) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getRow(point, columns) - 1 in 0 until rows
        override fun perform(point: Int, rows: Int, columns: Int) = point - columns
        override val reverse get() = S
    },
    E(0, 1) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getColumn(point, columns) + 1 in 0 until columns
        override fun perform(point: Int, rows: Int, columns: Int) = point + 1
        override val reverse get() = W
    },
    S(1, 0) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getRow(point, columns) + 1 in 0 until rows
        override fun perform(point: Int, rows: Int, columns: Int) = point + columns
        override val reverse get() = N
    },
    W(0, -1) {
        override fun isValid(point: Int, rows: Int, columns: Int) = getColumn(point, columns) - 1 in 0 until columns
        override fun perform(point: Int, rows: Int, columns: Int) = point - 1
        override val reverse get() = E
    };

    abstract fun isValid(point: Int, rows: Int, columns: Int): Boolean
    abstract fun perform(point: Int, rows: Int, columns: Int): Int
    abstract val reverse: Step6

}

private data class Path6(
        var start: Int, val color: Int, var steps: MutableList<Step6>, var end: Int = start,
        var stub: Boolean = false,
        var full: Boolean = false
)

private inline fun <T> twice(a: T, b: T, crossinline action: (T) -> Unit) {
    action(a)
    action(b)
}

fun main(): Unit = level { input ->
    val nt = input.nextInt()
    val results = StringBuilder()

    results.append(nt).append(" ")

    for (t in 0 until nt) {
        val rows = input.nextInt()
        val columns = input.nextInt()
        val n = input.nextInt()
        val grid = IntArray(rows * columns + 1)
        val pathGrid = IntArray(rows * columns + 1) { -1 }

        val colors = IntArray(n + 2)

        for (i in 0 until n) {
            val point = input.nextInt()
            val color = input.nextInt()
            grid[point] = color

            if (colors[color * 2] != 0) colors[color * 2 + 1] = point
            else colors[color * 2] = point
        }

        val np = input.nextInt()

        val paths = Array(n) {
            val start = colors[it + 2]
            val path = Path6(start, it / 2 + 1, ArrayList())
            pathGrid[start] = it
            path
        }

        fun findStep(point: Int, start: Int, color: Int, path: Int): Step6? {
            var chosen: Step6? = null
            for (step in Step6.values()) {
                if (step.isValid(point, rows, columns)) {
                    val result = step.perform(point, rows, columns)
                    if (result != start && pathGrid[result] != path) {
                        var set = false
                        if (grid[result] == 0 || (grid[result] == -color && paths[pathGrid[result]].end == result)) {
                            set = true
                        }
                        if (!set && grid[result] == color) {
                            set = true
                        }
                        if (set) {
                            if (chosen != null) {
                                // This isn't a sure move
                                return null
                            }
                            chosen = step
                        }
                    }
                }
            }
            return chosen
        }

        while (true) {
            var found = false
            for (pathIndex in 0 until paths.size) {
                val path = paths[pathIndex]
                if (path.stub) continue
                if (path.full) continue

                val choice = findStep(path.end, path.start, path.color, pathIndex)
                if (choice != null) {
                    val result = choice.perform(path.end, rows, columns)
                    if (grid[result] == path.color) {
                        // Create path
                        println("Creating full ${path.color} path from partial path")
                        val owningIndex = pathGrid[result]
                        paths[owningIndex].stub = true
                        paths[owningIndex].steps.clear()
                        paths[owningIndex] = path

                        if (path.start < result) {
                            path.steps.add(choice)
                            path.full = true
                        } else {
                            // Reverse the path
                            val newSteps = ArrayList<Step6>()
                            newSteps.add(choice.reverse)
                            newSteps.addAll(path.steps.map { it.reverse }.reversed())

                            path.end = path.start
                            path.start = result

                            path.steps = newSteps
                            path.full = true
                        }
                    } else if (grid[result] == -path.color) {
                        // Join partial paths
                        println("Joining partial ${path.color} paths")
                        val owningIndex = pathGrid[result]
                        val owningPath = paths[owningIndex]
                        if (path.start < owningPath.start) {
                            // Reverse the other path
                            println("Joining ${path.steps} ${owningPath.steps}")
                            path.end = owningPath.start
                            path.full = true

                            path.steps.add(choice)
                            path.steps.addAll(owningPath.steps.map { it.reverse }.reversed())
                            println(path.steps)

                            // Clear the other path
                            owningPath.steps.clear()
                            owningPath.stub = true
                            paths[owningIndex] = path
                        } else {
                            // Reverse this path
                            val oldSteps = path.steps
                            path.end = path.start
                            path.start = owningPath.start
                            path.full = true

                            path.steps = owningPath.steps
                            path.steps.add(choice.reverse)
                            path.steps.addAll(oldSteps.map { it.reverse }.reversed())

                            // Clear the other path
                            owningPath.steps = mutableListOf()
                            owningPath.stub = true
                            paths[owningIndex] = path
                        }
                    } else {
                        // Extend partial path
                        println("Extending partial ${path.color} path to $result")
                        path.end = result
                        path.steps.add(choice)
                        grid[result] = -path.color
                        pathGrid[result] = pathIndex
                    }
                    found = true
                }
            }

            if (!found) break
        }

        val sorted = paths.sortedWith(compareBy(Path6::color, Path6::start)).filter { it.steps.isNotEmpty() }.distinct()
        results.append(sorted.size).append(" ")
        for (path in sorted) {
            results.append(path.color).append(" ")
            results.append(path.start).append(" ")
            results.append(path.steps.size).append(" ")
            results.append(path.steps.joinToString(" ")).append(" ")
        }
    }

    output(results)
}