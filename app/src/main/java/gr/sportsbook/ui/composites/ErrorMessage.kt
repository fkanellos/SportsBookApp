package gr.sportsbook.ui.composites

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import gr.sportsbook.ui.components.AppText

@Composable
fun ErrorMessage(error: Throwable?) {
    AppText(text = error?.message ?: "Unknown Error", color = Color.Red)
}