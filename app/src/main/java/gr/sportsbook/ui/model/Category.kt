package gr.sportsbook.ui.model

// Sample data class
data class Category(
    val title: String,
    val games: List<GameUiModel> // This should match what you assign in ViewModel
)
data class GameUiModel(
    val eventId: String,
    val team1: String,
    val team2: String,
    val eventStartingTime: Long,
    var isFavorite: Boolean = false
)



