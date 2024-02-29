package gr.sportsbook.ui.composites

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gr.sportsbook.ui.components.AppText
import gr.sportsbook.utils.toCountdownFormat
import kotlinx.coroutines.delay

@Composable
fun CountdownTimer(eventStartTimeMillis: Long, currentTimeMillis: Long = System.currentTimeMillis()) {
    var timeRemainingMillis by remember { mutableStateOf(eventStartTimeMillis - currentTimeMillis) }

    LaunchedEffect(key1 = timeRemainingMillis) {
        while (timeRemainingMillis > 0) {
            delay(1000L)
            timeRemainingMillis = eventStartTimeMillis - System.currentTimeMillis()
        }
    }

    val formattedTime = if (timeRemainingMillis > 0) {
        timeRemainingMillis.toCountdownFormat()
    } else {
        "Event started"
    }

    AppText(text = formattedTime, modifier = Modifier.padding(8.dp))
}

