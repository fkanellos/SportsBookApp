package gr.sportsbook.utils

import gr.sportsbook.data.model.Sport
import gr.sportsbook.data.model.SportEvent
import java.util.concurrent.TimeUnit

fun List<Sport>.sortSportsFavorites(): List<Sport> {
    return this.map { sport ->
        sport.copy(events = sport.events.sortedWith(
            compareByDescending<SportEvent> { it.isFavorite }.thenBy { it.eventId }
        ))
    }
}
fun List<SportEvent>.sortSportEventByFavorites(): List<SportEvent> {
    return this.sortedWith(
        compareByDescending<SportEvent> { it.isFavorite }.thenBy { it.eventId }
    )
}

fun Long.toCountdownFormat(): String {
    val hours = TimeUnit.MILLISECONDS.toHours(this) % 60
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}