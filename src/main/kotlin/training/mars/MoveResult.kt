package training.mars

sealed class MoveResult {

    data class Ok(val distance: Double, val x: Double?, val y: Double?, val angle: Double?) : MoveResult()
    data class Pass(val key: String, val totalDistance: Double) : MoveResult()
    data class Error(val reason: String) : MoveResult()

}