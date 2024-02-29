package gr.sportsbook.ui.model

// Sample data class
data class Sport(
    val sportName: String,
    val events: List<SportEvent>
)
data class SportEvent(
    val eventId: String,
    val team1: String,
    val team2: String,
    val eventStartingTime: Int,
    var isFavorite: Boolean = false
)



