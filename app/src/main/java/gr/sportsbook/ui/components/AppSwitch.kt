package gr.sportsbook.ui.components

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppSwitch(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier
) {
    if (onCheckedChange != null) {
        // The switch itself handles the state change
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = modifier
        )
    } else {
        // The switch is visually interactive but doesn't change its state
        Switch(
            checked = checked,
            onCheckedChange = {},
            modifier = modifier
        )
    }
}
