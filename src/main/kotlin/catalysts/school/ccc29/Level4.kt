package catalysts.school.ccc29

import catalysts.Vector3
import catalysts.output
import java.util.*

private val buildings = LinkedList<Building4>()
private val seen = HashSet<Point>()

private data class Building4(val tiles: LinkedList<Vector3>) {

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

    buildings.push(Building4(LinkedList(listOf(p.toVector3(height)))))
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

    val ranges = input.nextInt()

    repeat(ranges) {
        segments.add(Pair(input.nextDouble(), input.nextInt()))
    }

    output(buildString {
        for ((index, building) in ordered) {
            var guards = 0
            for (tile in building.tiles) {
                val flat = tile.xy
                val distance = distanceFromCenter(flat)

                val needed = requiredGuards(distance, segments)
                guards += needed
            }
            append("$index $guards")
            append('\n')
        }
    })
}