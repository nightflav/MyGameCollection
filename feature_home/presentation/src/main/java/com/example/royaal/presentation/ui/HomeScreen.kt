package com.example.royaal.presentation.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.royaal.commonui.CardConst
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.LoadingPlaceholder
import com.example.royaal.commonui.multipleselector.MultipleSelector
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.ProfileModel
import com.example.royaal.presentation.HomeGamesCategory
import com.example.royaal.presentation.HomeScreenState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
internal fun HomeMain(
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
            ),
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
        games = { games },
        onGameClick = onGameClick,
    )
}

@Composable
internal fun Profile(
    profile: ProfileModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DimConst.smallPadding)
    ) {
        AsyncImage(
            model = profile.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(CardConst.halfCornerRadiusPercent))
                .aspectRatio(1f, matchHeightConstraintsFirst = true),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = profile.name,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = profile.secondName,
                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun GamesCarousel(
    games: () -> List<PreviewGameModel>,
    onGameClick: (Int) -> Unit,
    autoScrollDuration: Long = 5000L
) {
    val conf = LocalConfiguration.current
    val screenWidth = conf.screenWidthDp
    val carouselItemSpacing = DimConst.defaultPadding
    val carouselPadding = (screenWidth / 1.4 * 0.2).dp
    val carouselItemSize = (screenWidth / 1.4).dp
    val pagerState = rememberPagerState {
        games().size
    }
    val pagerScope = rememberCoroutineScope()
    val pageOffset by remember {
        derivedStateOf {
            (pagerState.currentPageOffsetFraction)
                .absoluteValue
        }
    }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()
    if (!isDragged) {
        with(pagerState) {
            if (pageCount > 0) {
                var currentPageKey by remember { mutableIntStateOf(0) }
                LaunchedEffect(key1 = currentPageKey) {
                    launch {
                        delay(timeMillis = autoScrollDuration)
                        val nextPage = (currentPage + 1).mod(pageCount)
                        animateScrollToPage(
                            page = nextPage,
                            animationSpec = tween(durationMillis = 2000)
                        )
                        currentPageKey = nextPage
                    }
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(carouselItemSize),
            pageSpacing = carouselItemSpacing,
            contentPadding = PaddingValues(horizontal = carouselPadding),
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(100),
            ),
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            GamePreviewItem(
                game = games()[index],
                onGameClick = {
                    if (pagerState.currentPage == index) onGameClick(it) else {
                        pagerScope.launch {
                            pagerState.animateScrollToPage(
                                page = index,
                                animationSpec = tween(1000)
                            )
                        }
                    }
                },
                modifier = Modifier
                    .pagerToCarouselItem(
                        pagerState = pagerState,
                        page = index,
                    )
                    .fillMaxWidth()
            )
        }

        if (games().isNotEmpty()) {
            Box {
                Text(
                    text = games()[pagerState.currentPage % games().size].name,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    modifier = Modifier.alpha(
                        (1F - pageOffset * 4).coerceAtLeast(0F)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GamePreviewItem(
    game: PreviewGameModel,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(272.dp)
            .height(445.dp),
        onClick = { onGameClick(game.id) }
    ) {
        AsyncImage(
            model = game.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxHeight(),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun Modifier.pagerToCarouselItem(
    pagerState: PagerState,
    page: Int,
) = graphicsLayer {
    val pageOffset =
        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

    when {
        pageOffset in 0F..0.2F -> {
            alpha = 1F - 2 * pageOffset
            scaleX = 1F - .5F * pageOffset
            scaleY = 1F - .5F * pageOffset
        }

        pageOffset in -0.2F..0F -> {
            alpha = 1F + 2 * pageOffset
            scaleX = 1F + .5F * pageOffset
            scaleY = 1F + .5F * pageOffset
        }

        pageOffset > .15F || pageOffset < -.15F -> {
            alpha = .6F
            scaleX = .9F
            scaleY = .9F
        }
    }

}
