package com.example.royaal.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.royaal.presentation.HomeGamesCategory
import com.example.royaal.presentation.HomeViewModel
import kotlinx.coroutines.launch

@Composable
internal fun HomeRoute(
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val homeScope = rememberCoroutineScope()
    when {
        state.isLoading || state.category == HomeGamesCategory.Undefined -> LoadingScreen()
        state.error != null -> ErrorScreen(state.error!!)
        else -> if (state.games.isNotEmpty()) HomeMain(
            state = state,
            modifier = modifier,
            onGameClick = onGameClick,
            onChangeCategory = { newCategory ->
                homeScope.launch {
                    viewModel.eventQueue.send(
                        HomeViewModel.HomeScreenEvents.SelectCategory(newCategory)
                    )
                }
            },
        )
    }
}