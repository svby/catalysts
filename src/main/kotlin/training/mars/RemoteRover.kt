package training.mars

class RemoteRover(
    val uuid: String,
    val wheelBase: Double,
    val maxSteerAngle: Double,
    val targetX: Double,
    val targetY: Double,
    val targetRadius: Double
) {

    companion object {

        @JvmStatic
        fun create(
            map: String,
            username: String = System.getProperty("catcoder.username"),
            contestId: String = "practice"
        ): RemoteRover? {
            return RoverRest.retrieveRover(RoverRest.createRover(map, username, contestId) ?: return null)
        }

    }

    override fun toString() =
        "RemoteRover(uuid='$uuid', wheelBase=$wheelBase, maxSteerAngle=$maxSteerAngle, targetX=$targetX, targetY=$targetY, targetRadius=$targetRadius)"

    fun move(distance: Double, steeringAngle: Double) = RoverRest.moveRover(uuid, distance, steeringAngle)

}