package gr.sportsbook.data.remote.dao

import com.squareup.moshi.Json

data class SportsResponseItem(
    @field:Json(name = "d")
    val sportName: String,
    @field:Json(name = "e")
    val activeEvents: List<GameDetailsInfo>
)
