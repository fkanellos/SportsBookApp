package gr.sportsbook.domain.repository

import gr.sportsbook.data.remote.dao.SportsResponseItem
import kotlinx.coroutines.flow.Flow

interface SportsRepository {
    suspend fun getSportsEvents(): Flow<List<SportsResponseItem>>
}
