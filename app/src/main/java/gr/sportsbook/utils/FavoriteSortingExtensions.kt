package gr.sportsbook.utils

import android.util.Log
import gr.sportsbook.ui.model.Sport
import gr.sportsbook.ui.model.SportEvent

fun List<Sport>.sortSportsFavorites(): List<Sport> {
    Log.i("mainviewmodel","sortFavorites")
    return this.map { sport ->
        sport.copy(events = sport.events.sortedWith(
            compareByDescending<SportEvent> { it.isFavorite }.thenBy { it.eventId }
        ))
    }
}
fun List<SportEvent>.sortSportEventByFavorites(): List<SportEvent> {
    Log.i("mainviewmodel", "sortFavorites")
    return this.sortedWith(
        compareByDescending<SportEvent> { it.isFavorite }.thenBy { it.eventId }
    )
}