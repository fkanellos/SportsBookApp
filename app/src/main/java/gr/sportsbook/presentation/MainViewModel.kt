package gr.sportsbook.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.sportsbook.data.remote.dao.GameDetailsInfo
import gr.sportsbook.data.remote.dao.SportsResponseItem
import gr.sportsbook.domain.errorHandling.ErrorAction
import gr.sportsbook.domain.errorHandling.ErrorState
import gr.sportsbook.domain.errorHandling.ErrorType
import gr.sportsbook.domain.preferences.Preferences
import gr.sportsbook.domain.repository.SportsRepository
import gr.sportsbook.ui.model.Sport
import gr.sportsbook.ui.model.SportEvent
import gr.sportsbook.utils.sortSportEventByFavorites
import gr.sportsbook.utils.sortSportsFavorites
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val sportsRepository: SportsRepository,
    private val prefs: Preferences
) : ViewModel() {

    private val _sportsEvents = MutableLiveData<List<Sport>>(emptyList())
    val sportsEvents: LiveData<List<Sport>> = _sportsEvents

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isDarkTheme = MutableLiveData(prefs.isToggleEnabled("darkTheme"))
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _sportSortingPreferences = mutableMapOf<String, MutableLiveData<Boolean>>()

    private val _uiRefreshTrigger = MutableLiveData<Boolean>()
    val uiRefreshTrigger: LiveData<Boolean> = _uiRefreshTrigger

    private val _errorState = MutableLiveData<ErrorState>()
    val errorState: LiveData<ErrorState> = _errorState


    init {
        fetchDataIfNeeded()
    }

    private fun fetchDataIfNeeded() {
        _sportsEvents.value.takeIf { it.isNullOrEmpty() }?.let { fetchData() }
    }

    fun fetchData() {
        Log.i("MainViewModel", "fetchData")
        viewModelScope.launch {
            _isLoading.value = true
            try {
                sportsRepository.getSportsEvents().collect { data ->
                    _sportsEvents.value = data.mapToSports()
                    _isLoading.value = false
                    loadFavorites()
                    clearErrorState()
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    // Convert API response to list of Sport
    private fun List<SportsResponseItem>.mapToSports() = map { it.toSport() }

    // Convert individual SportsResponseItem to Sport
    private fun SportsResponseItem.toSport() = Sport(
        sportName = sportName,
        events = activeEvents.map { it.toSportEvent() }
    )

    // Convert GameDetailsInfo to SportEvent
    private fun GameDetailsInfo.toSportEvent(): SportEvent {
        val (team1, team2) = eventName.split(" - ", limit = 2)
        return SportEvent(
            team1 = team1,
            team2 = team2,
            eventStartingTime = eventStartingTime,
            eventId = eventId
        )
    }

    fun favoriteIcon(eventId: String, sportName: String) {
        Log.i("mainviewmodel", "favoriteIcon")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                val isSortingEnabled =
                    getSortingPreferenceForSport(sportName).value ?: false
                if (currentList != null) {
                    val updatedList = currentList.map { sport ->
                        sport.copy(events = sport.events.map { game ->
                            if (game.eventId == eventId) game.copy(isFavorite = !game.isFavorite) else game
                        })
                    }
                    if (isSortingEnabled) {
                        _sportsEvents.postValue(updatedList.sortSportsFavorites())
                    } else {
                        _sportsEvents.postValue(updatedList)
                    }
                    saveFavorites()
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun loadFavorites() {
        Log.i("mainviewmodel", "loadFavorites")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                if (currentList != null) {
                    val favoriteIds = prefs.loadFavorites()
                    val updatedList = currentList.map { sport ->
                        val isSortEnabled =
                            getSortingPreferenceForSport(sport.sportName).value ?: false
                        val updatedGames = sport.events.map { game ->
                            game.copy(isFavorite = favoriteIds.contains(game.eventId))
                        }

                        if (isSortEnabled) {
                            sport.copy(events = updatedGames.sortSportEventByFavorites())
                        } else {
                            sport.copy(events = updatedGames)
                        }
                    }

                    _sportsEvents.postValue(updatedList)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun saveFavorites() {
        val favoriteIds = _sportsEvents.value
            ?.flatMap { it.events }
            ?.filter { it.isFavorite }
            ?.map { it.eventId }
            ?.toSet()

        prefs.saveFavorites(favoriteIds.orEmpty())
    }

    fun toggleTheme(isEnabled: Boolean) {
        _isDarkTheme.value = isEnabled
        prefs.setToggleEnabled("darkTheme", isEnabled)
    }

    fun getSortingPreferenceForSport(sport: String): LiveData<Boolean> {
        Log.i("mainviewmodel", "getSortingPreferenceForSport")
        if (!_sportSortingPreferences.containsKey(sport)) {
            _sportSortingPreferences[sport] = MutableLiveData(prefs.isToggleEnabled(sport))
        }
        return _sportSortingPreferences[sport]!!
    }

    fun toggleSportSorting(sport: String) {
        Log.i("mainviewmodel", "toggleSportSorting")
        val currentValue = _sportSortingPreferences[sport]?.value ?: false
        _sportSortingPreferences[sport]?.value = !currentValue
        prefs.setToggleEnabled(sport, !currentValue)

        if (!currentValue) {
            sortFavoritesBySport(sport)
        } else {
            resetSportSort(sport)
        }
    }

    private fun sortFavoritesBySport(sport: String) {
        Log.i("mainviewmodel", "sortFavoritesBySport")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                if (currentList != null) {
                    val updatedList = currentList.map { sportItem ->
                        if (sportItem.sportName == sport && sportItem.events.any { it.isFavorite }) {
                            // Sort the games within this Sport
                            sportItem.copy(events = sportItem.events.sortedWith(
                                compareByDescending<SportEvent> { it.isFavorite }.thenBy { it.eventStartingTime }
                            ))
                        } else {
                            sportItem
                        }
                    }
                    _sportsEvents.postValue(updatedList)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun resetSportSort(sport: String) {
        Log.i("mainviewmodel", "resetSportSort")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                if (currentList != null) {
                    val updatedList = currentList.map { sportItem ->
                        if (sportItem.sportName == sport) {
                            // Reset the games sorting within this Sport
                            sportItem.copy(events = sportItem.events.shuffled())
                        } else {
                            sportItem
                        }
                    }
                    _sportsEvents.postValue(updatedList)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }


    fun refreshUI() {
        _uiRefreshTrigger.value = true
    }

    fun resetUIRefreshTrigger() {
        _uiRefreshTrigger.value = false
    }

    private fun handleError(exception: Throwable) {
        _isLoading.value = false
        val errorType = when (exception) {
            is IOException -> ErrorType.NETWORK
            is HttpException -> ErrorType.SERVER
            else -> ErrorType.GENERAL
        }

        val errorMessage = when (errorType) {
            ErrorType.NETWORK -> "The connection has been lost. Please check your internet connection."
            ErrorType.SERVER -> "Cannot connect to the server. Please try again later."
            ErrorType.GENERAL -> "Something went wrong. Please try again later."
            else -> ""
        }

        val errorAction = when (errorType) {
            ErrorType.NETWORK -> ErrorAction("Retry") { fetchData() }
            else -> null
        }

        _errorState.value = ErrorState(
            message = errorMessage,
            errorType = errorType,
            action = errorAction
        )
    }

    fun clearErrorState() {
        _errorState.value = ErrorState(
            message = "",
            errorType = ErrorType.NONE,
            action = null
        )
    }
}
