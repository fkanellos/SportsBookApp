package gr.sportsbook.presentation.ui.sportsicons

import gr.sportsbook.R

enum class SportIcon(val drawableRes: Int) {
    SOCCER(R.drawable.football),
    BASKETBALL(R.drawable.basketball),
    TENNIS(R.drawable.tennis),
    TABLE_TENNIS(R.drawable.ping_pong),
    VOLLEYBALL(R.drawable.volleyball),
    ESPORTS(R.drawable.esports),
    ICE_HOCKEY(R.drawable.ice_hockey),
    HANDBALL(R.drawable.handball),
    BEACH_VOLLEYBALL(R.drawable.volleyball),
    SNOOKER(R.drawable.snooker),
    BADMINTON(R.drawable.badminton),
    FUTSAL(R.drawable.football),
    DARTS(R.drawable.darts);

    companion object {
        fun getSportIconByName(title: String): SportIcon {
            val formattedTitle = title.replace(" ", "_").uppercase()
            return try {
                valueOf(formattedTitle)
            } catch (e: IllegalArgumentException) {
                SOCCER
            }
        }
    }
}