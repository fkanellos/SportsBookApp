package gr.sportsbook.ui.screens

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.withStarted
import gr.sportsbook.domain.errorHandling.ErrorType
import gr.sportsbook.ui.components.Loader
import gr.sportsbook.ui.composites.CategoryCard
import gr.sportsbook.ui.composites.SportsBookTopBar
import gr.sportsbook.viewmodels.MainViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SportsBookScreen(
    onProfileIconClick: () -> Unit,
    onSettingsIconClick: () -> Unit
) {
    val viewModel: MainViewModel = hiltViewModel(LocalContext.current as ComponentActivity)
    val sportsEvents by viewModel.sportsEvents.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = true)
    val errorState by viewModel.errorState.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.clearErrorState()
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.withStarted {
            viewModel.fetchData()
        }
    }


    LaunchedEffect(errorState) {
        errorState?.let { error ->
            if (error.errorType != ErrorType.NONE) {
                val result = snackbarHostState.showSnackbar(
                    message = error.message ?: "An unexpected error occurred",
                    actionLabel = error.action?.label
                )
                if (result == SnackbarResult.ActionPerformed) {
                    error.action?.action?.invoke()
                }
                viewModel.clearErrorState()
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { SportsBookTopBar(onProfileIconClick, onSettingsIconClick) }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (isLoading) {
                Loader()
            } else if (sportsEvents.isNotEmpty()) {
                LazyColumn(contentPadding = paddingValues) {
                    items(sportsEvents) { sportCategory ->
                        CategoryCard(
                            title = sportCategory.title,
                            items = sportCategory.games,
                            onFavoriteClick = { viewModel.favoriteIcon(it, sportCategory.title) },
                            viewModel
                        )
                    }
                }
            }
        }
    }
}
