package com.example.royaal.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.royaal.commonui.DimConst
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.presentation.R

internal fun LazyListScope.foundGames(
    games: List<PreviewGameModel>,
    onGameClick: (Int) -> Unit,
    onShowMoreGames: () -> Unit
) {
    items(count = games.size, key = { games[it].id }, contentType = { games[it]::class }) {
        FoundGame(game = games[it]) {
            onGameClick(games[it].id)
        }
    }
    item {
        Button(onClick = onShowMoreGames) {
            Text(text = stringResource(id = R.string.show_more_games))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoundGame(
    game: PreviewGameModel, onGameClick: () -> Unit
) {
    Card(
        onClick = onGameClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(DimConst.defaultPadding)
        ) {
            AsyncImage(
                model = game.posterUrl,
                contentDescription = game.name,
                contentScale = ContentScale.Crop,
                filterQuality = FilterQuality.Medium,
                modifier = Modifier
                    .height(96.dp)
                    .aspectRatio(16 / 9f)
                    .clip(RoundedCornerShape(24.dp))
            )
            GameInfo(game)
        }
    }
}

@Composable
private fun GameInfo(game: PreviewGameModel) {
    Column {
        Text(
            text = game.name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = (game.releaseDate ?: ""),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Normal
        )
    }
}