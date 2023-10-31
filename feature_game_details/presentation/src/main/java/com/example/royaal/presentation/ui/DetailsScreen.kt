package com.example.royaal.presentation.ui

import android.text.Html
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.expandabletext.ExpandableText
import com.example.royaal.commonui.utilelements.Separator
import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.presentation.DetailsScreenState
import com.example.royaal.presentation.DetailsScreenState.Companion.game
import com.example.royaal.presentation.R

@Composable
internal fun DetailsScreen(
    modifier: Modifier = Modifier,
    state: DetailsScreenState,
    onSimilarGameClick: (Int) -> Unit,
    onPlatformClick: (Int) -> Unit,
    onBackPressed: () -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Box {
                AsyncImage(
                    model = state.backgroundImg,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3625f)
                        .aspectRatio(1f)
                        .clip(
                            RoundedCornerShape(
                                bottomStartPercent = 10,
                                bottomEndPercent = 10
                            )
                        )
                        .align(Alignment.Center),
                    contentScale = ContentScale.FillHeight
                )
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = onBackPressed
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
        item {
            GameInfo(
                game = state.game,
                onPlatformClick = onPlatformClick
            )
        }
        item {
            if (state.similarGames.isNotEmpty()) {
                Separator(width = 2.dp)
                SimilarGames(
                    onSimilarGameClick = onSimilarGameClick,
                    similarGames = state.similarGames
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(128.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun GameInfo(
    modifier: Modifier = Modifier,
    game: DetailsGameModel,
    onPlatformClick: (Int) -> Unit
) {
    val screenshotState = rememberPagerState {
        game.screenshots.size
    }
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = game.name,
            style = MaterialTheme.typography.titleLarge
        )
        if (game.screenshots.isNotEmpty())
            HorizontalPager(
                state = screenshotState,
                contentPadding = PaddingValues(horizontal = 16.dp),
                pageSpacing = DimConst.doublePadding,
                pageSize = PageSize.Fill
            ) { index ->
                ScreenshotItem(url = game.screenshots[index].url)
            }
        Text(
            text = stringResource(id = R.string.description_title),
            style = MaterialTheme.typography.titleLarge,
        )
        ExpandableText(
            text = Html.fromHtml(game.description, Html.FROM_HTML_MODE_LEGACY).toString(),
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
            modifier = Modifier.padding(DimConst.defaultPadding),
            maxLines = 5,
            showLessText = stringResource(id = R.string.show_less),
            showMoreText = stringResource(id = R.string.show_more),
        )
        Separator(
            width = 2.dp
        )
        Platforms(
            platforms = game.platforms,
            onPlatformClick = onPlatformClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Platforms(
    modifier: Modifier = Modifier,
    platforms: List<Platform>,
    onPlatformClick: (Int) -> Unit
) {
    FlowRow(
        modifier = modifier.padding(2.dp),
        horizontalArrangement = Arrangement.spacedBy(DimConst.smallPadding),
        verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding)
    ) {
        Text(
            text = stringResource(id = R.string.platforms_title),
            modifier = Modifier
        )
        platforms.forEach { platform ->
            PlatformItem(
                modifier = Modifier,
                platform = platform,
                onPlatformClick = onPlatformClick
            )
        }
    }
}

@Composable
private fun PlatformItem(
    modifier: Modifier = Modifier,
    platform: Platform,
    onPlatformClick: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {
                onPlatformClick(platform.id)
            }
            .widthIn(min = 84.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = platform.name,
            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
private fun ScreenshotItem(
    url: String
) {
    Card(
        shape = RoundedCornerShape(15),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.6f, false),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun SimilarGames(
    modifier: Modifier = Modifier,
    onSimilarGameClick: (Int) -> Unit,
    similarGames: List<PreviewGameModel>
) {
    Column(
        modifier = modifier.padding(horizontal = DimConst.doublePadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(DimConst.defaultPadding),
        content = {
            Text(
                text = stringResource(id = R.string.similar_games),
                style = MaterialTheme.typography.titleLarge,
            )
            for (game in similarGames) {
                SimilarGamePreview(
                    game = game,
                    onSimilarGameClick = onSimilarGameClick
                )
            }
        }
    )
}

@Composable
private fun SimilarGamePreview(
    modifier: Modifier = Modifier,
    game: PreviewGameModel,
    onSimilarGameClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        shape = RoundedCornerShape(15)
    ) {
        AsyncImage(
            model = game.posterUrl,
            contentDescription = game.name,
            modifier = Modifier
                .clickable {
                    onSimilarGameClick(game.id)
                }
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}