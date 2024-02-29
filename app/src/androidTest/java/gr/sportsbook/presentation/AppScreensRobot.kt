package gr.sportsbook.presentation

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import gr.sportsbook.navigation.Route
import kotlinx.coroutines.runBlocking

class AppScreensRobot(
    private val composeRule: AppScreensComposeRule
) {
    fun navigateTo(navigation: Route): AppScreensRobot {
        runBlocking {
            composeRule.awaitIdle()
            composeRule.runOnUiThread {
                navigation.SETTINGS
            }
        }
        return this
    }

    fun clickDarkMode(): AppScreensRobot {
        runBlocking {
            composeRule.awaitIdle()
            composeRule.onNodeWithContentDescription("Toggle Dark Mode", ignoreCase = true).performClick()
        }
        return this
    }

}