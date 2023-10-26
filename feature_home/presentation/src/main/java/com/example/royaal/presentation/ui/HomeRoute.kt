package com.example.royaal.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.royaal.commonui.CardConst
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.commonui.multipleselector.MultipleSelector
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.presentation.HomeGamesCategory
import com.example.royaal.presentation.HomeScreenState
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

@Composable
private fun HomeMain(
    state: HomeScreenState,
    modifier: Modifier = Modifier,
    onGameClick: (Int) -> Unit,
    onChangeCategory: (HomeGamesCategory) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(DimConst.doublePadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Profile(
                profile = state.profile,
                modifier = Modifier
                    .height(92.dp)
                    .weight(7f)
            )
        }
        Box(modifier = Modifier.padding(DimConst.defaultPadding)) {
            MultipleSelector(
                options = HomeGamesCategory.allCategories(),
                selectedOption = state.category.toString(),
                onOptionSelect = {
                    val newCategory = HomeGamesCategory.getCategoryByName(it)
                    onChangeCategory(newCategory)
                },
                modifier = Modifier.height(36.dp)
            )
        }
        Games(
            areGamesLoading = state.areGamesLoading,
            games = state.games,
            onGameClick = onGameClick
        )
        Box(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(CardConst.fullCornerRadiusPercent),
                    color = MaterialTheme.colorScheme.onSurface
                )
                .height(4.dp)
                .width((LocalConfiguration.current.screenWidthDp * 0.8).dp)
        )
    }
}

@Composable
private fun Games(
    areGamesLoading: Boolean,
    games: List<PreviewGameModel>,
    onGameClick: (Int) -> Unit
) {
    if (areGamesLoading) {
        LoadingPlaceholder()
    }
    GamesCarousel(
        games = games,
        onGameClick = onGameClick,
    )
}