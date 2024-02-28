//package gr.sportsbook.viewmodels
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import gr.sportsbook.data.repository.SportsEventResult
//import gr.sportsbook.domain.preferences.Preferences
//import gr.sportsbook.domain.repository.SportsRepository
//import gr.sportsbook.ui.model.Category
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.After
//import org.junit.Assert
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//import org.mockito.junit.MockitoJUnitRunner
//
//@RunWith(MockitoJUnitRunner::class)
//class MainViewModelTest {
//
//    @Mock
//    private lateinit var mockSportsRepository: SportsRepository
//
//    @Mock
//    private lateinit var mockPreferences: Preferences
//
//    private lateinit var viewModel: MainViewModel
//
//    @Before
//    fun setUp() {
//        // Initialize the MainViewModel with mocked dependencies
//        viewModel = MainViewModel(mockSportsRepository, mockPreferences)
//    }
//
//    @Test
//    fun `test fetchData when repository returns success`() {
//        // Arrange
//        val sportsEvents = listOf(/* mock data */)
//        Mockito.`when`(mockSportsRepository.getSportsEvents()).thenReturn(flowOf(SportsEventResult.Success(sportsEvents)))
//
//        // Act
//        viewModel.fetchData()
//
//        // Assert
//        Assert.assertEquals(/* expected result */, /* observed result from LiveData */)
//    }
//
//    // Additional tests for other methods and scenarios...
//}
//
