package catalysts.training.trail

import catalysts.Vector2
import java.util.*

enum class Direction(val vector: Vector2) {

    North(Vector2.Y), East(Vector2.X), South(-Vector2.Y), West(-Vector2.X);

    fun left() = values()[(ordinal + 4 - 1) % 4]

    fun right() = values()[(ordinal + 1) % 4]

}

data class Move(val netPositionChange: Vector2, val netDirectionChange: Int)

class Jim {

    var direction = Direction.North
        private set

    var position = Vector2.ZERO
        private set

    var distance = 0
        private set

    private val _vertices = LinkedList<Vector2>(listOf(Vector2.ZERO))
    val vertices: List<Vector2> = Collections.unmodifiableList(_vertices)

    fun move(count: Int) {
        _vertices.add(position + direction.vector * (count / 2.0))
        position += direction.vector * count
        _vertices.add(position)
        distance++
    }

    fun turnLeft(count: Int) {
        repeat(count) {
            direction = direction.left()
        }
    }

    fun turnRight(count: Int) {
        repeat(count) {
            direction = direction.right()
        }
    }

    fun stop() {
        _vertices.add(Vector2.ZERO)
    }

}