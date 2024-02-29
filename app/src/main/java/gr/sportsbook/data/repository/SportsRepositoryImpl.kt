package gr.sportsbook.data.repository

import gr.sportsbook.data.remote.api.SportsApi
import gr.sportsbook.data.remote.dao.SportsResponseItem
import gr.sportsbook.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class SportsRepositoryImpl @Inject constructor(
    private val sportsApi: SportsApi
) : SportsRepository {
    override suspend fun getSportsEvents(): Flow<List<SportsResponseItem>> = flow {
        try {
            val response = sportsApi.getSportsEvents()
            if (response.isSuccessful) {
                emit(response.body() ?: emptyList())
            } else {
                throw HttpException(response)
            }
        } catch (e: HttpException) {
            // Handle HTTP exceptions, such as a 404 or 500 error from the server
            throw Exception("HTTP error: ${e.code()}")
        } catch (e: IOException) {
            // Handle IO exceptions, such as a network request failing due to no internet connection
            throw Exception("Network error: ${e.localizedMessage}")
        }
    }
}

