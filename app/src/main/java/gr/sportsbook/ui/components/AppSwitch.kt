package gr.sportsbook.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    tint: Color,
    contentDescription: String
) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        thumbContent = {
            SportIcon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier.size(SwitchDefaults.IconSize),
                tint = tint
            )
        }
    )
}
