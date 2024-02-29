package gr.sportsbook.utils

import gr.sportsbook.ui.model.Sport
import gr.sportsbook.ui.model.SportEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.stream.Stream

class SportEventUtilsTest {
    companion object {
        @JvmStatic
        fun provideSportsData(): Stream<List<Sport>> {
            return Stream.of(
                (1..10).map { i ->
                    Sport(
                        "Sport$i",
                        (1..10).map { j ->
                            SportEvent(
                                eventId = "$j",
                                team1 = "Team1-$j",
                                team2 = "Team2-$j",
                                eventStartingTime = 10000 * j,
                                isFavorite = j % 2 == 0
                            )
                        }
                    )
                }
            )
        }
    }

    @ParameterizedTest
    @MethodSource("provideSportsData")
    fun `test sortSportsFavorites`(sports: List<Sport>) {
        val sortedSports = sports.sortSportsFavorites()
        sortedSports.forEach { sport ->
            Assertions.assertTrue(sport.events[0].isFavorite)
        }
    }
    @ParameterizedTest
    @MethodSource("provideSportsData")
    fun `test sortSportEventByFavorites`(sportsList: List<Sport>) {
        sportsList.forEach { sportItem ->
            val sortedEvents = sportItem.events.sortSportEventByFavorites()
            Assertions.assertTrue(sortedEvents[0].isFavorite)
        }
    }
    @ParameterizedTest
    @ValueSource(longs = [3600000, 1800000, 0, -1000, 86400000, 5000, 3661000])
    fun `test toCountdownFormat`(millis: Long) {
        val formatted = millis.toCountdownFormat()
        when (millis) {
            3600000L -> Assertions.assertEquals("01:00:00", formatted)
            1800000L -> Assertions.assertEquals("00:30:00", formatted)
            0L -> Assertions.assertEquals("00:00:00", formatted)
            86400000L -> Assertions.assertEquals("24:00:00", formatted)
            5000L -> Assertions.assertEquals("00:00:05", formatted)
            3661000L -> Assertions.assertEquals("01:01:01", formatted)
        }
    }
}