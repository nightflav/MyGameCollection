package com.example.royaal.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.presentation.ExploreViewModel

@Composable
internal fun ExploreRoute(
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel,
    onGameClick: (Int) -> Unit,
    onDeveloperClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit,
    onGoToAssistant: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    when {
        state.isLoading -> LoadingPlaceholder()
        state.error != null -> Text(text = "${state.error!!.message}")
        else -> ExploreScreen(
            modifier = modifier,
            state = state,
            onGameClick = onGameClick,
            onDeveloperClick = onDeveloperClick,
            onPlatformClick = onPlatformClick,
            onGoToAssistant = onGoToAssistant,
            onSelectPlatform = { newPlatform ->
                viewModel.sendEvent(ExploreViewModel.ExploreScreenEvent.SelectPlatform(newPlatform))
            },
            onSelectDeveloper = { newDev ->
                viewModel.sendEvent(ExploreViewModel.ExploreScreenEvent.SelectPlatform(newDev))
            },
            onSearchForGames = {
                viewModel.sendEvent(ExploreViewModel.ExploreScreenEvent.SearchForGames)
            },
            onQueryChange = { query ->
                viewModel.sendEvent(ExploreViewModel.ExploreScreenEvent.QueryChange(query))
            },
        )
    }
}