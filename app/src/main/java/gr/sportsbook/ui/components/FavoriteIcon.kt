package gr.sportsbook.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import gr.sportsbook.R

@Composable
fun FavoriteIcon(isFavorite: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        val image = if (isFavorite) R.drawable.favorite_selected_icon else R.drawable.favorite_unselected_icon
        Image(
            painter = painterResource(id = image),
            contentDescription = if (isFavorite) "Favorite" else "Star",
            modifier = Modifier.padding(8.dp)
        )
    }
}


