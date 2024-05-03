package com.example.royaal.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastAny
import coil.compose.AsyncImage
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.shimmers.ShimmerColumn
import com.example.royaal.commonui.shimmers.ShimmerRow
import com.example.royaal.commonui.utilelements.Separator
import com.example.royaal.core.common.model.uimodel.Developer
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.UiModel
import com.example.royaal.presentation.ExploreGamesState
import com.example.royaal.presentation.ExploreScreenState
import com.example.royaal.presentation.R
import com.example.royaal.presentation.SearchState

@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    state: ExploreScreenState,
    onGameClick: (Int) -> Unit,
    onDeveloperClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit,
    onSelectPlatform: (Int) -> Unit,
    onSelectDeveloper: (Int) -> Unit,
    onSearchForGames: () -> Unit,
    onQueryChange: (String) -> Unit,
    onGoToAssistant: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                elevation = FloatingActionButtonDefaults.elevation(2.dp),
                onClick = onGoToAssistant,
                shape = RoundedCornerShape(100)
            ) {
                Icon(
                    imageVector = Icons.Default.SmartToy,
                    contentDescription = null
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { it ->
        Column(
            modifier = modifier.padding(it),
            horizontalAlignment = CenterHorizontally
        ) {
            var isSearchBarActive by remember { mutableStateOf(false) }
            val keyboardState = keyboardAsState()
            GamesSearchField(
                searchState = state.searchState,
                onQueryChange = { onQueryChange(it) },
                onActiveChange = { isSearchBarActive = it },
                active = isSearchBarActive && keyboardState.value,
                onSearch = {
                    onSearchForGames()
                    isSearchBarActive = false
                },
                onCloseSearchBar = {
                    isSearchBarActive = false
                }
            )
            if (state.searchState.searchQuery.isNotEmpty()) {
                SearchStatefulResults(
                    state = state.searchState,
                    onGameClick = onGameClick,
                    onDeveloperClick = onDeveloperClick,
                    onPlatformClick = onPlatformClick,
                    onSearchForGames = onSearchForGames,
                    onQueryChange = onQueryChange
                )
            } else {
                SearchedList(
                    title = stringResource(id = R.string.upcoming_title),
                    isLoading = state.upcomingGamesState.areUpcomingLoading,
                    onItemClick = onGameClick,
                    items = { state.upcomingGamesState.upcoming }
                )
                SearchedList(
                    title = stringResource(id = R.string.latest_title),
                    isLoading = state.latestGamesState.areLatestLoading,
                    onItemClick = onGameClick,
                    items = { state.latestGamesState.latest })
                ExploreByPlatform(
                    exploreGamesState = state.exploreGamesState,
                    onGameClick = onGameClick,
                    onSelectPlatform = onSelectPlatform
                )
            }
        }
    }
}

@Composable
private fun SearchedList(
    title: String,
    isLoading: Boolean,
    onItemClick: (Int) -> Unit,
    items: () -> List<PreviewGameModel>
) {
    Text(
        modifier = Modifier.padding(vertical = DimConst.smallPadding),
        text = title
    )
    if (!isLoading) {
        GamesRow(
            games = items,
            onGameClick = onItemClick,
        )
    } else
        ShimmerRow(
            height = 160.dp,
            width = 320.dp,
            spaceBetween = DimConst.smallPadding
        )
}

@Composable
private fun SearchStatefulResults(
    state: SearchState,
    onGameClick: (Int) -> Unit,
    onDeveloperClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit,
    onSearchForGames: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    if (state.isLoading) {
        ShimmerColumn(
            height = 96.dp, contentPadding = PaddingValues(
                vertical = DimConst.defaultPadding
            ), spaceBetween = DimConst.defaultPadding
        )
    } else if (state.searchResult.isNotEmpty()) {
        SearchResults(
            result = { state.searchResult },
            onGameClick = onGameClick,
            onDeveloperClick = onDeveloperClick,
            onPlatformClick = onPlatformClick
        )
    } else {
        FailedSearchRequest(onRetry = {
            onSearchForGames()
        }, onDismiss = {
            onQueryChange("")
        })
    }
}

@Composable
private fun SearchResults(
    result: () -> List<UiModel>,
    onGameClick: (Int) -> Unit,
    onDeveloperClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
        contentPadding = PaddingValues(
            vertical = DimConst.defaultPadding,
        ),
        horizontalAlignment = CenterHorizontally
    ) {
        foundGames(games = result().filterIsInstance<PreviewGameModel>().take(10),
            onGameClick = onGameClick,
            onShowMoreGames = {

            })
        if (result().fastAny { it is Developer }) {
            item {
                Column {
                    Text(text = stringResource(id = R.string.developers_title))
                    Separator()
                }
            }
            foundDevelopers(
                devs = result().filterIsInstance<Developer>(),
                onDevClick = { onDeveloperClick(it) })
        }
        if (result().fastAny { it is Platform }) {
            item {
                Column {
                    Text(text = stringResource(id = R.string.platforms_title))
                    Separator()
                }
            }
            foundPlatforms(platforms = result().filterIsInstance<Platform>(),
                onPlatformClick = { onPlatformClick(it) })
        }
    }
}

@Composable
private fun ExploreByPlatform(
    exploreGamesState: ExploreGamesState,
    onGameClick: (Int) -> Unit,
    onSelectPlatform: (Int) -> Unit
) {
    Column {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
            contentPadding = PaddingValues(horizontal = DimConst.mediumPadding)
        ) {
            items(count = exploreGamesState.platforms.size) { index ->
                val it = exploreGamesState.platforms[index]
                PlatformCard(
                    isSelected = it.name == exploreGamesState.selectedPlatform, platform = it
                ) { newPlatform ->
                    onSelectPlatform(newPlatform)
                }
            }
        }
        GamesRow(games = { exploreGamesState.explore }, onGameClick = onGameClick)
    }
}

@Composable
private fun PlatformCard(
    isSelected: Boolean, platform: Platform, onSelectPlatform: (Int) -> Unit
) = Card(
        shape = RoundedCornerShape(50),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = if (isSelected) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSecondaryContainer
        ),
        modifier = Modifier.widthIn(min = 84.dp),
        onClick = { onSelectPlatform(platform.id) },
    ) {
        Text(
            text = platform.name, modifier = Modifier
                .padding(
                    horizontal = DimConst.defaultPadding, vertical = DimConst.smallPadding
                )
                .align(CenterHorizontally)
        )
    }

@Composable
private inline fun GamesRow(
    crossinline games: () -> List<PreviewGameModel>,
    crossinline onGameClick: (Int) -> Unit,
) = LazyRow(
    modifier = Modifier,
    horizontalArrangement = Arrangement.spacedBy(DimConst.smallPadding),
    contentPadding = PaddingValues(horizontal = DimConst.doublePadding),
    verticalAlignment = CenterVertically
) {
    items(
        count = games().size
    ) {
        GameCard(game = games()[it]) { id ->
            onGameClick(id)
        }
    }
}

@Composable
private fun GameCard(
    game: PreviewGameModel,
    onGameClick: (Int) -> Unit,
) = Card(
        onClick = { onGameClick(game.id) }, shape = RoundedCornerShape(24.dp), modifier = Modifier
    ) {
        AsyncImage(
            model = game.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(320.dp)
                .height(160.dp)
        )
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GamesSearchField(
    searchState: SearchState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    onCloseSearchBar: () -> Unit
) {
    SearchBar(
        modifier = Modifier.padding(horizontal = 4.dp),
        query = searchState.searchQuery,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
        onActiveChange = onActiveChange,
        windowInsets = WindowInsets.safeContent,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            AnimatedVisibility(
                visible = active && searchState.searchQuery.isNotEmpty(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Icon(imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close),
                    modifier = Modifier.clickable {
                        onQueryChange("")
                    }
                )
            }
        }) {
        searchState.previousQueries.forEach { request ->
            Row(
                modifier = Modifier
                    .padding(DimConst.defaultPadding)
                    .clickable {
                        onQueryChange(request)
                        onSearch(request)
                    }, verticalAlignment = CenterVertically
            ) {
                Icon(
                    modifier = Modifier.padding(horizontal = DimConst.smallPadding),
                    imageVector = Icons.Default.History,
                    contentDescription = stringResource(id = R.string.history),
                )
                Text(
                    text = request, style = MaterialTheme.typography.bodySmall
                )
            }
            }
        }
}

@Composable
private fun FailedSearchRequest(
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.no_result))
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DimConst.mediumPadding)
        ) {
            TextButton(
                onClick = onRetry, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            TextButton(
                onClick = onDismiss, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = stringResource(id = R.string.cancel),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
fun keyboardAsState(): State<Boolean> {
    val isImeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    return rememberUpdatedState(isImeVisible)
}

@Composable
@Preview(heightDp = 800, widthDp = 360)
private fun ExplorePreview() {
    MaterialTheme {
        ExploreScreen(state = ExploreScreenState(),
            onGameClick = {},
            onDeveloperClick = {},
            onPlatformClick = {},
            onSelectPlatform = {},
            onSelectDeveloper = {},
            onSearchForGames = {},
            onQueryChange = {},
            onGoToAssistant = {}
        )
    }
}