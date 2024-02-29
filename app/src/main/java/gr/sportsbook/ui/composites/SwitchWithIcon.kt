package gr.sportsbook.ui.composites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import gr.sportsbook.presentation.MainViewModel
import gr.sportsbook.ui.components.AppIcon
import gr.sportsbook.ui.components.AppSwitch
import gr.sportsbook.ui.components.AppText
import gr.sportsbook.ui.theme.ThemeIcon

@Composable
fun SwitchWithIcon(viewModel: MainViewModel) {
    val isDarkTheme by viewModel.isDarkTheme.observeAsState(false)
    val cardContainerColor = if (isDarkTheme) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer
    val contentColor = if (isDarkTheme) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
    val title = "Night Mode"
    val subtitle = if (!isDarkTheme) "Enable" else "Disable"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { viewModel.toggleTheme(!isDarkTheme) },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = cardContainerColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppIcon(
                iconRes = if (isDarkTheme) ThemeIcon.DARK_MODE.drawableRes else ThemeIcon.LIGHT_MODE.drawableRes,
                contentDescription = "Toggle Dark Mode",
                tint = contentColor
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                AppText(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )
                AppText(
                    text = subtitle,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            AppSwitch(
                checked = isDarkTheme,
                onCheckedChange = {viewModel.toggleTheme(!isDarkTheme)},
                icon = if (isDarkTheme) Icons.Default.Check else Icons.Default.Close,
                tint = if (isDarkTheme) Color.White else Color.Black,
                contentDescription = "darkMode toggle"
            )
        }
    }
}

