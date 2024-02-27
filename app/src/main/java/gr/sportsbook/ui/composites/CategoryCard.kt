package gr.sportsbook.ui.composites

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import gr.sportsbook.Constants
import gr.sportsbook.ui.components.AppText
import gr.sportsbook.ui.components.FavoriteIcon
import gr.sportsbook.ui.components.SportIcon
import gr.sportsbook.ui.model.GameUiModel
import gr.sportsbook.ui.sportsicons.SportIcon.Companion.getSportIconFromTitle

@Composable
fun CategoryCard(
    title: String,
    items: List<GameUiModel>,
    onFavoriteClick: (String) -> Unit
) {
    val sportIcon = getSportIconFromTitle(title)
    val imageVector = ImageVector.vectorResource(id = sportIcon.drawableRes)
    var isExpanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp)),
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                SportIcon(imageVector, title)
                AppText(modifier = Modifier.weight(1f), text = title)
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    SportIcon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                    )
                }
            }
            if (isExpanded) {
                LazyRow {
                    items(items) { item ->
                        Column {
                            CountdownTimer(eventStartTimeMillis = item.eventStartingTime * Constants.TIME_TO_MS)
                            FavoriteIcon(isFavorite = item.isFavorite, onClick = { onFavoriteClick(item.eventId) })
                            AppText(text = item.team1)
                            AppText(text = item.team2)
                        }
                    }
                }
            }
        }
    }
}


