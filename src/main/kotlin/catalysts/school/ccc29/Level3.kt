package catalysts.school.ccc29

import catalysts.Vector3
import catalysts.output
import java.util.*
import kotlin.math.ceil

private val buildings = LinkedList<Building3>()
private val seen = HashSet<Point>()

private data class Building3(val tiles: LinkedList<Vector3>) {

    fun calculateCenter(): Vector3 {
        var sum = Vector3.ZERO
        tiles.forEach { sum += it }
        return sum / tiles.size
    }

}

private fun preprocess(row: Int, column: Int) {
    val p = Point(row, column)

    val height = map[row][column]
    if (height == 0) return
    if (p in seen) return
    seen.add(p)

    buildings.push(Building3(LinkedList(listOf(p.toVector3(height)))))
    preprocess(row + 1, column, height)
    preprocess(row - 1, column, height)
    preprocess(row, column + 1, height)
    preprocess(row, column - 1, height)
}

private fun preprocess(row: Int, column: Int, height: Int) {
    val p = Point(row, column)
    if (p in seen) return

    if (row !in 0 until rows) return
    if (column !in 0 until columns) return

    if (map[row][column] == height) {
        buildings.peek().tiles.add(p.toVector3(height))
        seen.add(p)
        preprocess(row + 1, column, height)
        preprocess(row - 1, column, height)
        preprocess(row, column + 1, height)
        preprocess(row, column - 1, height)
    }
}

fun main(): Unit = Scanner(System.`in`).use { input ->
    rows = input.nextInt()
    columns = input.nextInt()

    map = Array(rows) { IntArray((columns)) }

    for (row in 0 until rows) {
        for (column in 0 until columns) {
            val height = input.nextInt()
            map[row][column] = height
        }
    }

    for (row in 0 until rows) {
        for (column in 0 until columns) {
            preprocess(row, column)
        }
    }

    println(buildings)

    val ordered = buildings.sortedBy { it.tiles.size }
            .mapIndexed { index, b -> Pair(index, b) }

//    output(ordered.joinToString(" ") { "${it.first} ${it.second.tiles.size}" })

    val pairs = input.nextInt()
    output(buildString {
        repeat(pairs) {
            val from = input.nextInt()
            val to = input.nextInt()

            val cm1 = ordered[from].second.calculateCenter()
            val cm2 = ordered[to].second.calculateCenter()

            println(cm1)
            println(cm2)

            append(ceil((cm2 - cm1).length).toInt())
            append('\n')
        }
    })
}