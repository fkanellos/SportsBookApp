package gr.sportsbook.data.repository

import gr.sportsbook.data.remote.api.SportsApi
import gr.sportsbook.data.remote.dao.SportsResponseItem
import gr.sportsbook.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

sealed class SportsEventResult {
    data class Success(val data: List<SportsResponseItem>) : SportsEventResult()
    data class Error(val exception: Throwable) : SportsEventResult()
}

class SportsRepositoryImpl @Inject constructor(
    private val sportsApi: SportsApi
) : SportsRepository {
    override suspend fun getSportsEvents(): Flow<SportsEventResult> {
        return flow {
            try {
                val response = sportsApi.getSportsEvents()
                if (response.isSuccessful) {
                    emit(SportsEventResult.Success(response.body() ?: emptyList()))
                } else {
                    emit(SportsEventResult.Error(Exception("Unsuccessful network call")))
                }
            } catch (e: Exception) {
                emit(SportsEventResult.Error(e))
            }
        }
    }
}
