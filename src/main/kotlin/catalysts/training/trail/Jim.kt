package catalysts.training.trail

import catalysts.Vector2

enum class Direction(val vector: Vector2) {

    North(Vector2.Y), East(Vector2.X), South(-Vector2.Y), West(-Vector2.X);

    fun left() = values()[(ordinal + 4 - 1) % 4]

    fun right() = values()[(ordinal + 1) % 4]

}

data class Move(val netPositionChange: Vector2, val netDirectionChange: Int)

class Jim {

    var direction = Direction.North
        private set

    var position = Vector2()
        private set

    var distance = 0
        private set

    fun move(count: Int) {
        position += direction.vector * count
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

}