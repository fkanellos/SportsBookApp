package gr.sportsbook

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import gr.sportsbook.data.remote.api.SportsApi
import gr.sportsbook.navigation.Route
import gr.sportsbook.navigation.SportsBookNavigation
import gr.sportsbook.presentation.ui.theme.SportsBookTheme
import gr.sportsbook.presentation.MainViewModel
import javax.inject.Inject
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sportsApi: SportsApi

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val isDarkTheme by viewModel.isDarkTheme.observeAsState(false)
            SportsBookTheme(isDarkTheme) {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) {
                    SportsBookNavigation(
                        navController = navController,
                        onProfileIconClick = { navController.navigate(Route.PROFILE) },
                        onSettingsIconClick = { navController.navigate(Route.SETTINGS) }
                    )
                }
            }
        }
    }
}
