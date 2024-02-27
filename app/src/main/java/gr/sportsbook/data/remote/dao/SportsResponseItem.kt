package gr.sportsbook.data.remote.dao

import com.squareup.moshi.Json

data class SportsResponseItem(
    @field:Json(name = "d")
    val sportTitle: String,
    @field:Json(name = "e")
    val gameInfo: List<GameDetailsInfo>
)
