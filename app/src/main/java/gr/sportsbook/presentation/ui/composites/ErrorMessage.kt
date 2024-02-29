package gr.sportsbook.presentation.ui.composites

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import gr.sportsbook.presentation.ui.components.AppText

@Composable
fun ErrorMessage(error: Throwable?) {
    AppText(text = error?.message ?: "Unknown Error", color = Color.Red)
}