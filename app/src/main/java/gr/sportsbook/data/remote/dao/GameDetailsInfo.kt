package gr.sportsbook.data.remote.dao

import com.squareup.moshi.Json

/* Using Moshi for JSON parsing due to its excellent Kotlin support and superior performance compared to Gson */

data class GameDetailsInfo(
    @field:Json(name = "d")
    val teamsInfo: String,
    @field:Json(name = "tt")
    val eventStartingTime: Long,
    @field:Json(name = "i")
    val eventId: String
)