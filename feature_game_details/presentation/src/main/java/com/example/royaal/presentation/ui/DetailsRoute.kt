package com.example.royaal.presentation.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlaylistAddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.PlaylistAddCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.commonui.fab.ExpandableFloatingActionButton
import com.example.royaal.commonui.fab.ExpandedFabOption
import com.example.royaal.presentation.DetailsViewModel

@Composable
internal fun DetailsRoute(
    onBackPressed: () -> Unit,
    onSimilarGameClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit,
    viewModel: DetailsViewModel,
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val options = listOf(
        ExpandedFabOption(
            name = "Favourite",
            onOptionSelected = {
                viewModel.sendEvent(
                    DetailsViewModel.DetailsScreenEvents.AddToFavourite
                )
                makeAnnouncement(msg = it.name, context)
            },
            icon = if (state.isFavourite) Icons.Filled.Favorite
            else Icons.Outlined.FavoriteBorder,
            shape = RoundedCornerShape(50)
        ),
        ExpandedFabOption(
            name = "Wishlist",
            onOptionSelected = {
                viewModel.sendEvent(
                    DetailsViewModel.DetailsScreenEvents.AddToWishlist
                )
                makeAnnouncement(msg = it.name, context)
            },
            icon = if (state.isInWishList) Icons.Filled.PlaylistAddCircle
            else Icons.Outlined.PlaylistAddCircle,
            shape = RoundedCornerShape(50)
        ),
        ExpandedFabOption(
            name = "Completed",
            onOptionSelected = {
                viewModel.sendEvent(
                    DetailsViewModel.DetailsScreenEvents.AddToCompleted
                )
                makeAnnouncement(msg = it.name, context)
            },
            icon = if (state.isCompleted) Icons.Default.CheckCircle
            else Icons.Outlined.CheckCircle,
            shape = RoundedCornerShape(50)

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
                    mainButtonHeight = 64.dp,
                    needToRotate = true,
                    expansionDuration = 500,
                    spaceBetweenOptions = 4.dp
                )
            }
        ) {
            DetailsScreen(
                state = state,
                onSimilarGameClick = onSimilarGameClick,
                onPlatformClick = onPlatformClick,
                modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                onBackPressed = onBackPressed
            )
        }
    }
}

private fun makeAnnouncement(msg: String, context: Context) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}