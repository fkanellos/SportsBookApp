package gr.sportsbook.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun SportIcon(imageVector: ImageVector, contentDescription: String) {
    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = Color.Unspecified,
        modifier = Modifier
            .padding(8.dp)
            .size(24.dp)
    )
}