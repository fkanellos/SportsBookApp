package gr.sportsbook.data.repository

import gr.sportsbook.data.remote.api.SportsApi
import gr.sportsbook.data.remote.dao.SportsResponseItem
import gr.sportsbook.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SportsRepositoryImpl @Inject constructor(
    private val sportsApi: SportsApi
) : SportsRepository {
    override suspend fun getSportsEvents(): Flow<List<SportsResponseItem>> = flow {
        val response = sportsApi.getSportsEvents()
        if (response.isSuccessful) {
            emit(response.body() ?: emptyList())
        } else {
            throw Exception("Unsuccessful network call")
        }
    }
}
