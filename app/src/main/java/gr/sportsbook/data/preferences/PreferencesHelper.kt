package gr.sportsbook.data.preferences

import android.content.SharedPreferences
import gr.sportsbook.domain.preferences.Preferences

class PreferencesHelper(
    private val sharedPreferences: SharedPreferences
) : Preferences {

    override fun saveFavorites(favoriteIds: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet("favorites", favoriteIds)
        editor.apply()
    }

    override fun loadFavorites(): Set<String> {
        return sharedPreferences.getStringSet("favorites", emptySet()) ?: emptySet()
    }

    override fun isDarkThemeEnabled(): Boolean {
        return sharedPreferences.getBoolean("dark_theme", false)
    }

    override fun setDarkThemeEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean("dark_theme", isEnabled).apply()
    }

    override fun setToggleEnabled(toggle: String, isEnabled: Boolean) {
        sharedPreferences.edit().putBoolean(toggle, isEnabled). apply()
    }

    override fun isToggleEnabled(toggle: String): Boolean {
        return sharedPreferences.getBoolean(toggle, false)
    }
}
