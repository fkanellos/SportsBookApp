//package gr.sportsbook.ui.elements
//
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.material3.Divider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import gr.sportsbook.ui.model.Category
//import gr.sportsbook.ui.model.GameUiModel
//
////@Composable
////fun SportsEventsList(sportsEvents: List<Category>, onFavoriteClick: (Game) -> Unit) {
////    LazyColumn {
////        items(sportsEvents) { category ->
////            CategoryCard(
////                title = category.title,
////                items = category.games,
////                onFavoriteClick = onFavoriteClick
////            )
////        }
////    }
////}
//
//
//@Composable
//fun SportsEventsList(sportsEvents: List<Category>, onToggleFavorite: (String) -> Unit) {
//    LazyColumn(modifier = Modifier.fillMaxSize()) {
//        items(sportsEvents) { category ->
//            CategoryItem(category, onToggleFavorite)
//        }
//    }
//}
//
//@Composable
//fun CategoryItem(category: Category, onToggleFavorite: (String) -> Unit) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        SportIcon(sport = category.title)
//        Divider()
//        category.games.forEach { game ->
//            GameItem(game, onToggleFavorite)
//        }
//    }
//}
//
//@Composable
//fun SportIcon(sport: String) {
//    // Assuming you have a SportIcon composable that takes the sport name and returns an icon
//    Row(verticalAlignment = Alignment.CenterVertically) {
//        // Replace this Icon with your SportIcon composable
//        Icon(imageVector = Icons.Default.SportsSoccer, contentDescription = null)
//        CustomText(text = sport)
//    }
//}
//
//@Composable
//fun CustomText(text: String) {
//    // This is a placeholder for your custom Text composable
//    Text(text = text, style = MaterialTheme.typography.body1)
//}
//
//@Composable
//fun GameItem(game: GameUiModel, onToggleFavorite: (String) -> Unit) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        SportText(
//            text = game.eventStartingTime,
//            modifier = Modifier
//                .weight(1f)
//                .padding(end = 8.dp)
//        )
//        IconButton(onClick = { onToggleFavorite(game.eventId) }) {
//            Icon(
//                imageVector = if (game.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
//                contentDescription = "Toggle Favorite",
//                modifier = Modifier.size(24.dp)
//            )
//        }
//        CustomText(
//            text = game.team1,
//            modifier = Modifier
//                .weight(2f)
//                .padding(end = 8.dp)
//        )
//        CustomText(
//            text = "-",
//            modifier = Modifier.padding(horizontal = 4.dp)
//        )
//        CustomText(
//            text = game.team2,
//            modifier = Modifier.weight(2f)
//        )
//    }
//}
//
