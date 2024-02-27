package gr.sportsbook.data.remote.api

import gr.sportsbook.data.remote.dao.SportsResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface SportsApi {
    @GET("sports")
    suspend fun getSportsEvents(): Response<List<SportsResponseItem>>
}