package gr.sportsbook.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gr.sportsbook.data.remote.dao.GameDetailsInfo
import gr.sportsbook.data.remote.dao.SportsResponseItem
import gr.sportsbook.data.repository.SportsEventResult
import gr.sportsbook.domain.preferences.Preferences
import gr.sportsbook.domain.repository.SportsRepository
import gr.sportsbook.ui.model.Category
import gr.sportsbook.ui.model.GameUiModel
import gr.sportsbook.utils.sortCategoryFavorites
import gr.sportsbook.utils.sortGameUiModelByFavorites
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

    private val _isDarkTheme = MutableLiveData(prefs.isToggleEnabled("darkTheme"))
    val isDarkTheme: LiveData<Boolean> = _isDarkTheme

    private val _categorySortingPreferences = mutableMapOf<String, MutableLiveData<Boolean>>()

    private val _uiRefreshTrigger = MutableLiveData<Boolean>()
    val uiRefreshTrigger: LiveData<Boolean> = _uiRefreshTrigger

    private val _error = MutableLiveData<Throwable?>()
    val error: LiveData<Throwable?> = _error


    init {
        fetchDataIfNeeded()
    }

    private fun fetchDataIfNeeded() {
        _sportsEvents.value.takeIf { it.isNullOrEmpty() }?.let { fetchData() }
    }

    private fun fetchData() {
        Log.i("MainViewModel", "fetchData")
        viewModelScope.launch {
            try {
                _isLoading.value = true
                sportsRepository.getSportsEvents().collect { result ->
                    handleSportsEventResult(result)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e
            }
        }
    }

    private fun handleSportsEventResult(result: SportsEventResult) {
        when (result) {
            is SportsEventResult.Success -> handleSuccessResult(result)
            is SportsEventResult.Error -> handleErrorResult(result)
        }
    }

    private fun handleSuccessResult(result: SportsEventResult.Success) {
        val categoriesList = result.data.mapToCategories()
        _sportsEvents.value = categoriesList
        _isLoading.value = false
        loadFavorites()
    }

    private fun handleErrorResult(result: SportsEventResult.Error) {
        _isLoading.value = false
        _error.value = result.exception
    }

    // Convert API response to list of Category
    private fun List<SportsResponseItem>.mapToCategories() = map { it.toCategory() }

    // Convert individual SportsResponseItem to Category
    private fun SportsResponseItem.toCategory() = Category(
        title = sportName,
        games = activeEvents.map { it.toGameUiModel() }
    )

    // Convert GameDetailsInfo to GameUiModel
    private fun GameDetailsInfo.toGameUiModel(): GameUiModel {
        val (team1, team2) = eventName.split(" - ", limit = 2)
        return GameUiModel(
            team1 = team1,
            team2 = team2,
            eventStartingTime = eventStartingTime,
            eventId = eventId
        )
    }


    fun favoriteIcon(eventId: String, sportCategoryTitleTitle: String) {
        Log.i("mainviewmodel", "favoriteIcon")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                val isSortingEnabled = getSortingPreferenceForCategory(sportCategoryTitleTitle).value ?: false
                if (currentList != null) {
                    val updatedList = currentList.map { category ->
                        category.copy(games = category.games.map { game ->
                            if (game.eventId == eventId) game.copy(isFavorite = !game.isFavorite) else game
                        })
                    }
                    if (isSortingEnabled) {
                        _sportsEvents.postValue(updatedList.sortCategoryFavorites())
                    } else {
                        _sportsEvents.postValue(updatedList)
                    }
                    saveFavorites()
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e
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
                    val updatedList = currentList.map { category ->
                        val isSortEnabled =
                            getSortingPreferenceForCategory(category.title).value ?: false
                        val updatedGames = category.games.map { game ->
                            game.copy(isFavorite = favoriteIds.contains(game.eventId))
                        }

                        if (isSortEnabled) {
                            category.copy(games = updatedGames.sortGameUiModelByFavorites())
                        } else {
                            category.copy(games = updatedGames)
                        }
                    }

                    _sportsEvents.postValue(updatedList)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e
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

    fun toggleTheme(isEnabled: Boolean) {
        _isDarkTheme.value = isEnabled
        prefs.setToggleEnabled("darkTheme", isEnabled)
    }

    fun getSortingPreferenceForCategory(category: String): LiveData<Boolean> {
        Log.i("mainviewmodel", "getSortingPreferenceForCategory")
        if (!_categorySortingPreferences.containsKey(category)) {
            _categorySortingPreferences[category] = MutableLiveData(prefs.isToggleEnabled(category))
        }
        return _categorySortingPreferences[category]!!
    }

    fun toggleCategorySorting(category: String) {
        Log.i("mainviewmodel", "toggleCategorySorting")
        val currentValue = _categorySortingPreferences[category]?.value ?: false
        _categorySortingPreferences[category]?.value = !currentValue
        prefs.setToggleEnabled(category, !currentValue)

        if (!currentValue) {
            sortFavoritesByCategory(category)
        } else {
            resetCategorySort(category)
        }
    }

    private fun sortFavoritesByCategory(category: String) {
        Log.i("mainviewmodel", "sortFavoritesByCategory")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                if (currentList != null) {
                    val updatedList = currentList.map { categoryItem ->
                        if (categoryItem.title == category && categoryItem.games.any { it.isFavorite }) {
                            // Sort the games within this category
                            categoryItem.copy(games = categoryItem.games.sortedWith(
                                compareByDescending<GameUiModel> { it.isFavorite }.thenBy { it.eventStartingTime }
                            ))
                        } else {
                            categoryItem
                        }
                    }
                    _sportsEvents.postValue(updatedList)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e
            }
        }
    }

    private fun resetCategorySort(category: String) {
        Log.i("mainviewmodel", "resetCategorySort")
        viewModelScope.launch {
            try {
                val currentList = _sportsEvents.value
                if (currentList != null) {
                    val updatedList = currentList.map { categoryItem ->
                        if (categoryItem.title == category) {
                            // Reset the games sorting within this category
                            categoryItem.copy(games = categoryItem.games.shuffled())
                        } else {
                            categoryItem
                        }
                    }
                    _sportsEvents.postValue(updatedList)
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _error.value = e
            }
        }
    }


    fun refreshUI() {
        _uiRefreshTrigger.value = true
    }

    fun resetUIRefreshTrigger() {
        _uiRefreshTrigger.value = false
    }
}
