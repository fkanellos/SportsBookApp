package gr.sportsbook.ui.composites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import gr.sportsbook.ui.components.ActionIconButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsBookTopBar(onProfileIconClick: () -> Unit, onSettingsIconClick: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text(text = "SportsBook") },
        actions = {
            ActionIconButton(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile",
                onClick = onProfileIconClick
            )
            ActionIconButton(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                onClick = onSettingsIconClick
            )
        }
    )
}
