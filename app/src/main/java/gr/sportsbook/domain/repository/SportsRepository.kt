package gr.sportsbook.domain.repository

import gr.sportsbook.data.repository.SportsEventResult
import kotlinx.coroutines.flow.Flow

interface SportsRepository {
    suspend fun getSportsEvents(): Flow<SportsEventResult>
}
