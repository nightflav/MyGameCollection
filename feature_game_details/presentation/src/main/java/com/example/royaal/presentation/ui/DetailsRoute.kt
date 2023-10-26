package com.example.royaal.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.commonui.fab.ExpandableFloatingActionButton
import com.example.royaal.commonui.fab.ExpandedFabOption
import com.example.royaal.presentation.DetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun DetailsRoute(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onSimilarGameClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit,
    viewModel: DetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val detailsScope = rememberCoroutineScope()
    val options = listOf(
        ExpandedFabOption(
            name = "Add to favourite",
            onOptionSelected = {
                detailsScope.launch {
                    viewModel.eventQueue.send(
                        DetailsViewModel.DetailsScreenEvents.AddToFavourite
                    )
                }
            },
            icon = Icons.Default.Favorite
        ),
        ExpandedFabOption(
            name = "Add to wishlist",
            onOptionSelected = {
                detailsScope.launch {
                    viewModel.eventQueue.send(
                        DetailsViewModel.DetailsScreenEvents.AddToWishlist
                    )
                }
            },
            icon = Icons.Default.Checklist
        ),
        ExpandedFabOption(
            name = "Add to completed",
            onOptionSelected = {
                detailsScope.launch {
                    viewModel.eventQueue.send(
                        DetailsViewModel.DetailsScreenEvents.AddToCompleted
                    )
                }
            },
            icon = Icons.Default.Check
        )
    )
    when {
        state.isLoading -> LoadingPlaceholder()
        state.error != null -> {}
        else -> Scaffold(
            floatingActionButton = {
                ExpandableFloatingActionButton(
                    expandedColor = MaterialTheme.colorScheme.tertiaryContainer,
                    mainButtonColor = MaterialTheme.colorScheme.tertiary,
                    options = options,
                    icon = Icons.Default.ExpandLess,
                    mainButtonWidth = 64.dp,
                    mainButtonHeight = 64.dp
                ) {

                }
            }
        ) {
            DetailsScreen(
                state = state,
                onSimilarGameClick = onSimilarGameClick,
                onPlatformClick = onPlatformClick,
                modifier = modifier.padding(it)
            )
        }
    }
}