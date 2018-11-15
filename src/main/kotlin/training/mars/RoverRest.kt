package training.mars

import com.mashape.unirest.http.Unirest

object RoverRest {

    const val BASE_URL = "http://rover.catalysts.cc/rover"

    fun createRover(map: String, username: String, contestId: String): String? {
        val uuid = Unirest.get("$BASE_URL/create")
            .queryString(
                mapOf(
                    "map" to map,
                    "username" to username,
                    "contestId" to contestId
                )
            ).asString()
            .body

        if (uuid.startsWith("ERROR")) {
            System.err.println(uuid)
            return null
        }
        return uuid
    }

    fun retrieveRover(uuid: String) =
        Unirest.get("$BASE_URL/{uuid}")
            .routeParam("uuid", uuid)
            .asString()
            .body.split(Regex(" +")).let {
            RemoteRover(uuid, it[0].toDouble(), it[1].toDouble(), it[2].toDouble(), it[3].toDouble(), it[4].toDouble())
        }

    fun moveRover(uuid: String, distance: Double, steeringAngle: Double): MoveResult {
        val body = Unirest.get("$BASE_URL/move/{uuid}")
            .routeParam("uuid", uuid)
            .queryString(
                mapOf(
                    "distance" to distance,
                    "steeringAngle" to steeringAngle
                )
            ).asString()
            .body.split(Regex(" +"))

        return when (body[0].toLowerCase()) {
            "ok" -> MoveResult.Ok(body[1].toDouble(), body[2].toDouble(), body[3].toDouble(), body[4].toDouble())
            "pass" -> MoveResult.Pass(body[1], body[2].toDouble())
            "error" -> MoveResult.Error(body[1])
            else -> throw NotImplementedError()
        }
    }

}
