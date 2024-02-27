package gr.sportsbook.ui.screens

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import gr.sportsbook.ui.composites.SwitchWithIcon
import gr.sportsbook.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackPress: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val uiRefreshTrigger by viewModel.uiRefreshTrigger.observeAsState()

        LaunchedEffect(uiRefreshTrigger) {
            if (uiRefreshTrigger == true) {
                // Perform actions to refresh the UI, like navigating back
                viewModel.refreshUI()
                // Reset the trigger
                viewModel.resetUIRefreshTrigger()
            }
        }
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Settings",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackPress()}) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            }
        ) { values ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                SwitchWithIcon(viewModel)

            }
        }
    }
}