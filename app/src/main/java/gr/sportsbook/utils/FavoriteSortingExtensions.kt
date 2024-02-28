package gr.sportsbook.utils

import android.util.Log
import gr.sportsbook.ui.model.Category
import gr.sportsbook.ui.model.GameUiModel

fun List<Category>.sortCategoryFavorites(): List<Category> {
    Log.i("mainviewmodel","sortFavorites")
    return this.map { category ->
        category.copy(games = category.games.sortedWith(
            compareByDescending<GameUiModel> { it.isFavorite }.thenBy { it.eventId }
        ))
    }
}
fun List<GameUiModel>.sortGameUiModelByFavorites(): List<GameUiModel> {
    Log.i("mainviewmodel", "sortFavorites")
    return this.sortedWith(
        compareByDescending<GameUiModel> { it.isFavorite }.thenBy { it.eventId }
    )
}