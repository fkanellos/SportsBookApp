package gr.sportsbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import gr.sportsbook.presentation.screens.ProfileScreen
import gr.sportsbook.presentation.screens.SettingsScreen
import gr.sportsbook.presentation.screens.SportsBookScreen

@Composable
fun SportsBookNavigation(
    navController: NavHostController,
    onProfileIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Route.SPORTSBOOK
    ) {
        composable(Route.SPORTSBOOK) {
            SportsBookScreen(
                onProfileIconClick = onProfileIconClick,
                onSettingsIconClick = onSettingsIconClick
            )
        }
        composable(Route.SETTINGS) {
            SettingsScreen(
                onBackPress = { navController.navigate(Route.SPORTSBOOK) }
            )
        }
        composable(Route.PROFILE) {
            ProfileScreen(
                onBackPress = { navController.navigate(Route.SPORTSBOOK) }
            )
        }
    }
}
