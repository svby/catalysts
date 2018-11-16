package catalysts.school.ccc29

import catalysts.Vector2
import catalysts.Vector3
import catalysts.output
import java.util.*
import kotlin.math.max

private val buildings = LinkedList<Building6>()
private val seen = HashSet<Point>()

private data class Building6(val tiles: LinkedList<Vector3>) {

    val center by lazy {
        var sum = Vector3.ZERO
        tiles.forEach { sum += it }
        sum / tiles.size
    }

    val guards by lazy {
        var guards = 0
        for (tile in tiles) {
            var maxGuards = Int.MIN_VALUE
            for (point in interestPoints) {
                val vec = Vector2(point.first - 0.5, point.second - 0.5)

                val distance = (tile.xy - vec).length
                val needed = requiredGuards(distance, segments)

                maxGuards = max(maxGuards, needed)
            }
            guards += maxGuards
        }
        guards
    }

}

private fun recur(row: Int, column: Int) {
    val p = Point(row, column)

    val height = map[row][column]
    if (height == 0) return
    if (p in seen) return
    seen.add(p)

    buildings.push(Building6(LinkedList(listOf(p.toVector3(height)))))
    recur(row + 1, column, height)
    recur(row - 1, column, height)
    recur(row, column + 1, height)
    recur(row, column - 1, height)
}

private fun recur(row: Int, column: Int, height: Int) {
    val p = Point(row, column)
    if (p in seen) return

    if (row !in 0 until rows) return
    if (column !in 0 until columns) return

    if (map[row][column] == height) {
        buildings.peek().tiles.add(p.toVector3(height))
        seen.add(p)
        recur(row + 1, column, height)
        recur(row - 1, column, height)
        recur(row, column + 1, height)
        recur(row, column - 1, height)
    }
}

private fun heliGreedyCenter(): Iterable<Int> {
    val ordered = buildings.sortedBy { it.tiles.size }
            .mapIndexed { index, b -> Pair(index, b) }

    val notVisited = ordered.map { it.first }.toMutableSet()
    var position = Vector3.ZERO

    var deployed = 0.0
    var guardsLeft = ordered.sumBy { it.second.guards }
    val visited = ArrayDeque<Int>()

    while (notVisited.isNotEmpty()) {
        val (nextIndex, next) = ordered
                .filter { it.first in notVisited }
                .minBy {
                    (position - it.second.center).length
                } ?: continue

        val travelDistance = (next.center - position).length
        val travelTime = travelDistance / velocity

        deployed += travelTime * guardsLeft

        guardsLeft -= next.guards
        notVisited.remove(nextIndex)
        visited.add(nextIndex)

        position = next.center

        println("at $position")
    }

    if (deployed <= threshold) println("Passed $deployed / $threshold")
    else println("Failed $deployed / $threshold")

    return visited
}

fun main(): Unit = Scanner(System.`in`).use { input ->
    rows = input.nextInt()
    columns = input.nextInt()
    threshold = input.nextInt()
    velocity = input.nextDouble()

    map = Array(rows) { IntArray((columns)) }

    outer@ for (row in 0 until rows) {
        for (column in 0 until columns) {
            val height = input.nextInt()
            map[row][column] = height
        }
    }

    outer@ for (row in 0 until rows) {
        for (column in 0 until columns) {
            recur(row, column)
        }
    }

    val points = input.nextInt()
    repeat(points) {
        interestPoints.add(Point(input.nextInt(), input.nextInt()))
    }

    val ranges = input.nextInt()

    repeat(ranges) {
        segments.add(Pair(input.nextDouble(), input.nextInt()))
    }

    // Processing done

    val processor = ::heliGreedyCenter

    val visited = processor()

    output(visited.joinToString(" "))
}