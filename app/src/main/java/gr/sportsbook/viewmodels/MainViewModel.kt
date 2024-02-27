package gr.sportsbook.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.sportsbook.data.repository.SportsEventResult
import gr.sportsbook.domain.preferences.Preferences
import gr.sportsbook.domain.repository.SportsRepository
import gr.sportsbook.ui.model.Category
import gr.sportsbook.ui.model.GameUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sportsRepository: SportsRepository,
    private val prefs: Preferences
) : ViewModel() {

    private val _sportsEvents = MutableLiveData<List<Category>>(emptyList())
    val sportsEvents: LiveData<List<Category>> = _sportsEvents

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDarkTheme = MutableLiveData(prefs.isDarkThemeEnabled())
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _uiRefreshTrigger = MutableLiveData<Boolean>()
    val uiRefreshTrigger: LiveData<Boolean> = _uiRefreshTrigger

    private val _error = MutableLiveData<Throwable?>()
    val error: LiveData<Throwable?> = _error




    init {
        fetchDataIfNeeded()
    }

    private fun fetchDataIfNeeded() {
        if (_sportsEvents.value.isNullOrEmpty()) {
            fetchData()
        }
    }

    private fun fetchData() {
        viewModelScope.launch {
            sportsRepository.getSportsEvents().collect { result ->
                when (result) {
                    is SportsEventResult.Success -> {
                        _isLoading.value = true
                        val categoriesList = result.data.map { sportsResponseItem ->
                            Category(
                                title = sportsResponseItem.sportTitle,
                                games = sportsResponseItem.gameInfo.map { gameDetailsInfo ->
                                    val (team1, team2) = gameDetailsInfo.teamsInfo.split(" - ", limit = 2)
                                    GameUiModel(
                                        team1 = team1,
                                        team2 = team2,
                                        eventStartingTime = gameDetailsInfo.eventStartingTime,
                                        eventId = gameDetailsInfo.eventId
                                    )
                                }
                            )
                        }
                        _sportsEvents.value = categoriesList
                        _isLoading.value = false

                        // Load favorites after fetching data
                        loadFavorites()
                    }
                    is SportsEventResult.Error -> {
                        _isLoading.value = false
                        _error.value = result.exception
                    }
                }
            }
        }
    }



    fun toggleFavorite(eventId: String) {
        viewModelScope.launch {
            val currentList = _sportsEvents.value
            if (currentList != null) {
                val updatedList = currentList.map { category ->
                    category.copy(games = category.games.map { game ->
                        if (game.eventId == eventId) game.copy(isFavorite = !game.isFavorite) else game
                    })
                }.sortFavorites()
                _sportsEvents.postValue(updatedList)

                // Save the updated favorites
                saveFavorites()
            }
        }
    }



    private fun List<Category>.sortFavorites(): List<Category> {
        return this.map { category ->
            category.copy(games = category.games.sortedWith(
                compareByDescending<GameUiModel> { it.isFavorite }.thenBy { it.eventId }
            ))
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            val currentList = _sportsEvents.value
            if (currentList != null) {
                val favoriteIds = prefs.loadFavorites()
                val updatedList = currentList.map { category ->
                    category.copy(games = category.games.map { game ->
                        game.copy(isFavorite = favoriteIds.contains(game.eventId))
                    })
                }.sortFavorites()

                _sportsEvents.postValue(updatedList)
            }
        }
    }



    private fun saveFavorites() {
        val favoriteIds = _sportsEvents.value
            ?.flatMap { it.games }
            ?.filter { it.isFavorite }
            ?.map { it.eventId }
            ?.toSet()

        prefs.saveFavorites(favoriteIds.orEmpty())
    }

    fun toggleTheme(isDark: Boolean) {
        _isDarkTheme.value = isDark
        prefs.setDarkThemeEnabled(isDark)
    }

    fun refreshUI() {
        _uiRefreshTrigger.value = true
    }
    fun resetUIRefreshTrigger() {
        _uiRefreshTrigger.value = false
    }
}
