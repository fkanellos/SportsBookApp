package gr.sportsbook.data.repository

import gr.sportsbook.data.remote.api.SportsApi
import gr.sportsbook.data.remote.dao.GameDetailsInfo
import gr.sportsbook.data.remote.dao.SportsResponseItem
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class SportsApiFake: SportsApi {
    var shouldReturnError = false
    private var errorCode = 200
    fun setErrorResponseCode(code: Int) {
        errorCode = code
        shouldReturnError = true
    }

    var sports = (1..10).map { sportId ->
        SportsResponseItem(
            sportName = "Sport $sportId",
            activeEvents = List(5) { eventId ->
                GameDetailsInfo(
                    eventName = "Event $eventId for Sport $sportId",
                    eventStartingTime = (1..100).random(), // Random starting time
                    eventId = "event$sportId-$eventId" // Construct an event ID
                )
            }
        )
    }
    override suspend fun getSportsEvents(): Response<List<SportsResponseItem>> {
        if (shouldReturnError) {
            return when (errorCode) {
                404 -> Response.error(404, "Bad Request".toResponseBody())
                500 -> Response.error(500, "Server Error".toResponseBody())
                else -> Response.error(errorCode, "Error".toResponseBody())
            }
        }
        return Response.success(sports)
    }
}