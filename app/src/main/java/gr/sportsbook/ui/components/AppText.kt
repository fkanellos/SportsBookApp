package gr.sportsbook.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun AppText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color? = null,
    style: TextStyle? = null
) {
    Text(
        text = text,
        color = color ?: Color.Unspecified, // Use the provided color or fallback to default if null
        style = style ?: MaterialTheme.typography.bodyMedium,
        modifier = modifier.then(Modifier.padding(8.dp)) // Apply the default padding along with any passed-in modifier
    )
}
