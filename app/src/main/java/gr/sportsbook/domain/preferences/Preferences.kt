package gr.sportsbook.domain.preferences

interface Preferences {
        fun saveFavorites(favoriteIds: Set<String>)
        fun loadFavorites(): Set<String>
        fun isDarkThemeEnabled(): Boolean
        fun setDarkThemeEnabled(isEnabled: Boolean)
        fun setToggleEnabled(toggle: String, isEnabled: Boolean)
        fun isToggleEnabled(toggle: String): Boolean
}