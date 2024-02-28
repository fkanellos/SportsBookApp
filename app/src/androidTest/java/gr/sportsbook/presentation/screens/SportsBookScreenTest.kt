package gr.sportsbook.presentation.screens

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import gr.sportsbook.MainActivity
import gr.sportsbook.navigation.Route
import gr.sportsbook.presentation.AppScreensRobot
import gr.sportsbook.ui.theme.SportsBookTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SportsBookScreenTest {


    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var navigation: Route

    @Before
    fun init() {
        hiltRule.inject()
        navigation = Route
    }

    @Test
    fun testSportsBookScreenUi() {
        composeRule.activity.setContent {
            println("setContent")
            SportsBookTheme {
                SportsBookScreen(
                    onProfileIconClick = { /*...*/ },
                    onSettingsIconClick = { /*...*/ }
                )
                println("iconsClicked")
            }

        }

        composeRule.onNodeWithContentDescription("Profile")
            .assertIsDisplayed()
            .performClick()
        println("run1")

        composeRule.onNodeWithContentDescription("Settings")
            .assertIsDisplayed()
            .performClick()
        println("run2")

        AppScreensRobot(composeRule)
            .navigateTo(navigation)
    }

    @Test
    fun navigate_to_settings_screen_click_dark_mode() {
        composeRule.activity.setContent {
            println("setContent")
            SportsBookTheme {
                SportsBookScreen(
                    onProfileIconClick = { /*...*/ },
                    onSettingsIconClick = { /*...*/ }
                )
                println("iconsClicked")
            }

        }
        AppScreensRobot(composeRule)
            .navigateTo(navigation)

        composeRule.activity.setContent {
            println("setContent")
            SportsBookTheme {
                SettingsScreen(
                    onBackPress = { /*...*/ }
                )
                println("iconsClicked")
            }
        }
        AppScreensRobot(composeRule)
            .clickDarkMode()
    }

    @Test
    fun fetch_data_and_test_favoriteIcon_favoriteToggle() {
        composeRule.activity.setContent {
            SportsBookTheme {
                SportsBookScreen(
                    onProfileIconClick = { /*...*/ },
                    onSettingsIconClick = { /*...*/ }
                )
            }
        }
        composeRule.waitUntil(timeoutMillis = 10_000) {
            val isIconDisplayed = try {
                composeRule.onAllNodesWithContentDescription("SportCard")
                true
            } catch (e: AssertionError) {
                false
            }
            isIconDisplayed

        }
    }
}