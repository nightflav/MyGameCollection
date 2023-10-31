package com.example.royaal.presentation.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.presentation.FavouriteViewModel

@Composable
internal fun FavouriteRoute(
    viewModel: FavouriteViewModel,
    onGameClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    when {
        state.isLoading -> {
            LoadingPlaceholder()
        }

        state.error != null -> {
            Text(text = "Error")
        }

        else -> FavouriteScreen(
            state = state,
            onGameClick = onGameClick,
            onSelectCategory = {
                viewModel.sendEvent(
                    FavouriteViewModel.FavouriteScreenEvents.SelectCategory(it)
                )
            }
        )
    }
}