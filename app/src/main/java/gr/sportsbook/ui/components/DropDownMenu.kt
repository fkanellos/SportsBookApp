
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import gr.sportsbook.ui.components.AppText

@Composable
fun DropDownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    items: Array<String>,
    onItemSelected: (Int) -> Unit
) {
    val rememberDismissRequest = rememberUpdatedState(onDismissRequest)

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = rememberDismissRequest.value
    ) {
        items.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = { AppText(modifier = Modifier, text = item) }, // Assuming Text is imported from androidx.compose.material3.Text
                onClick = {
                    onItemSelected(index)
                    rememberDismissRequest.value()
                }
            )
        }
    }
}
