//package gr.sportsbook.ui.screens
//
//import android.annotation.SuppressLint
//import androidx.activity.ComponentActivity
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.hilt.navigation.compose.hiltViewModel
//import gr.sportsbook.ui.elements.ErrorMessage
//import gr.sportsbook.ui.elements.Loader
//import gr.sportsbook.ui.elements.SportsEventsList
//import gr.sportsbook.ui.model.Category
//import gr.sportsbook.viewmodels.MainViewModel
//
//sealed class ScreenState
//data object LoaderState : ScreenState()
//class ContentState(val sportsEvents: List<Category>) : ScreenState()
//class ErrorState(val error: Throwable?) : ScreenState()
//
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SportsBookScreen(
//    onProfileIconClick: () -> Unit,
//    onSettingsIconClick: () -> Unit
//) {
//    val viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
//    val sportsEvents by viewModel.sportsEvents.observeAsState(initial = emptyList())
//    val isLoading by viewModel.isLoading.observeAsState(initial = true)
//    val error by viewModel.error.observeAsState()
//
//    val contentState = remember(isLoading, sportsEvents, error) {
//        derivedStateOf {
//            when {
//                isLoading -> LoaderState
//                error != null -> ErrorState(error)
//                else -> ContentState(sportsEvents)
//            }
//        }
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        when (val state = contentState.value) {
//            is LoaderState -> Loader()
//            is ContentState -> SportsEventsList(state.sportsEvents, viewModel::toggleFavorite)
//            is ErrorState -> ErrorMessage(state.error)
//        }
//    }
//}
package gr.sportsbook.ui.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import gr.sportsbook.ui.components.Loader
import gr.sportsbook.ui.composites.CategoryCard
import gr.sportsbook.ui.composites.SportsBookTopBar
import gr.sportsbook.viewmodels.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportsBookScreen(
    onProfileIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val sportsEvents by viewModel.sportsEvents.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = true)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (isLoading){
            Loader()
        } else {
            val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = { SportsBookTopBar(onProfileIconClick, onSettingsIconClick) }
            ) { paddingValues ->
                LazyColumn(contentPadding = paddingValues) {
                    items(sportsEvents) { category ->
                        CategoryCard(
                            title = category.title,
                            items = category.games,
                            onFavoriteClick = { viewModel.toggleFavorite(it) }

                        )
                    }
                }
            }
        }
    }
}
