package gr.sportsbook.data.repository

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class SportsRepositoryImplTest {
    private lateinit var repositoryImpl: SportsRepositoryImpl
    private lateinit var sportsApi: SportsApiFake

    @BeforeEach
    fun setUp() {
        sportsApi = SportsApiFake()
        repositoryImpl = SportsRepositoryImpl(sportsApi)
    }

    @Test
    fun `when response is successful, data is returned correctly`() = runBlocking {
        sportsApi.shouldReturnError = false
        val result = repositoryImpl.getSportsEvents().first()
        Assertions.assertTrue(result.isNotEmpty())
    }

    @Test
    fun `when response is error 400, client error exception is thrown`() {
        runBlocking {
            sportsApi.setErrorResponseCode(404)
            assertThrows<Exception> {
                repositoryImpl.getSportsEvents().first()
            }

        }
    }


    @Test
    fun `when response is error 500, server error exception is thrown`() {
        runBlocking {
            sportsApi.setErrorResponseCode(500)
            assertThrows<Exception> {
                repositoryImpl.getSportsEvents().first()
            }
        }
    }

}