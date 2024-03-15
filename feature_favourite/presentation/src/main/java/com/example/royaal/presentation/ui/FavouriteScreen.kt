package com.example.royaal.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.commonui.multipleselector.MultipleSelector
import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.presentation.FavouriteScreenCategory
import com.example.royaal.presentation.FavouriteScreenState

@Composable
internal fun FavouriteScreen(
    modifier: Modifier = Modifier,
    state: FavouriteScreenState,
    onGameClick: (Int) -> Unit,
    onSelectCategory: (FavouriteScreenCategory) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
        modifier = modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Text(
            text = state.selectedCategory.toString(),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(CenterHorizontally)
        )
        Box(modifier = Modifier.padding(DimConst.defaultPadding)) {
            MultipleSelector(
                options = FavouriteScreenCategory.allCategories(),
                selectedOption = state.selectedCategory.toString(),
                onOptionSelect = {
                    val category = FavouriteScreenCategory.getCategoryByName(it)
                    onSelectCategory(category)
                },
                modifier = Modifier.height(36.dp)
            )
        }
        if (!state.areGamesLoading) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
                contentPadding = PaddingValues(
                    horizontal = DimConst.smallPadding,
                    vertical = DimConst.mediumPadding
                ),
                horizontalArrangement = Arrangement.spacedBy(DimConst.smallPadding),
            ) {
                items(
                    key = { state.games[it].id },
                    count = state.games.size
                ) {
                    FavouriteItem(
                        state.games[it],
                        onGameClick
                    )
                }
            }
        } else {
            LoadingPlaceholder()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavouriteItem(
    game: DetailsGameModel,
    onGameClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(15),
        onClick = {
            onGameClick(game.id)
        },
        modifier = Modifier.aspectRatio(0.8f)
    ) {
        Box {
            AsyncImage(
                model = game.backgroundUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }
    }
}