package catalysts.school.ccc29

import catalysts.Vector2
import catalysts.Vector3
import java.util.*

typealias Point = Pair<Int, Int>

fun Point.toVector() = Vector2(first.toDouble(), second.toDouble())
fun Point.toVector3(height: Int) = Vector3(first.toDouble(), second.toDouble(), height.toDouble())

lateinit var map: Array<IntArray>
var rows = 0
var columns = 0
var threshold = 0
var velocity = 0.0
val segments = LinkedList<Pair<Double, Int>>()
val interestPoints = ArrayDeque<Point>()

fun requiredGuards(distance: Double, ranges: List<Pair<Double, Int>>): Int {
    for (segment in ranges) {
        val rangeEnd = segment.first
        if (distance <= rangeEnd) return segment.second
    }
    return 0
}

val center by lazy {
    Vector2((rows - 1) / 2.0, (columns - 1) / 2.0)
}

fun distanceFromCenter(vector: Vector2): Double {
    return (center - vector).length
}