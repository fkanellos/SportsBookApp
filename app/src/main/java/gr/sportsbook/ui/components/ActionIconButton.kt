package gr.sportsbook.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun ActionIconButton(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription
        )
    }
}
