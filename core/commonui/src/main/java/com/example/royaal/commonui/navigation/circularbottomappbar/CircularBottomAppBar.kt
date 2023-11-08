package com.example.royaal.commonui.navigation.circularbottomappbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private sealed interface MultiRoute {
    data object Route : MultiRoute
    data object Background : MultiRoute
}

data class CircularBarRoute(
    val icon: ImageVector,
    val selectedColor: Color? = null,
    val unselectedColor: Color? = null,
    val name: String,
    val route: String,
    val onNavigateToRoute: () -> Unit
)

@Stable
interface CircularBottomAppBarState {

    val selectedRouteIndex: Float
    val routeContentColors: List<Color>

    fun selectRoute(scope: CoroutineScope, newRoute: Int)
}

@Stable
class CircularBottomAppBarStateImpl(
    routes: List<CircularBarRoute>,
    selectedRoute: CircularBarRoute,
    private val selectedContentColor: Color,
    private val unselectedContentColor: Color
) : CircularBottomAppBarState {

    override val selectedRouteIndex: Float
        get() = _selectedRoute.value

    override val routeContentColors: List<Color>
        get() = _routeContentColors.value

    private val _selectedRoute = Animatable(routes.indexOf(selectedRoute).toFloat())

    private var _routeContentColors: State<List<Color>> = derivedStateOf {
        List(routes.size) { index ->
            lerp(
                start = unselectedContentColor,
                stop = selectedContentColor,
                fraction = 1f - (selectedRouteIndex - index.toFloat())
                    .absoluteValue
                    .coerceAtMost(1f)
            )
        }
    }

    private val animationSpec = spring<Float>(stiffness = Spring.StiffnessLow)

    override fun selectRoute(scope: CoroutineScope, newRoute: Int) {
        scope.launch {
            _selectedRoute.animateTo(
                newRoute.toFloat(), animationSpec
            )
        }
    }
}

@Composable
fun rememberCircularAppBarState(
    routes: List<CircularBarRoute>,
    selectedRoute: CircularBarRoute,
    selectedContentColor: Color,
    unselectedContentColor: Color
) = remember {
    CircularBottomAppBarStateImpl(
        routes = routes,
        selectedRoute = selectedRoute,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor
    )
}

@Composable
fun CircularBottomAppBar(
    modifier: Modifier = Modifier,
    selectedRoute: CircularBarRoute,
    bottomAppBarShape: Shape = RectangleShape,

    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    selectorColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    selectedContentColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    unselectedContentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,

    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,

    navBarHeight: Dp = (LocalConfiguration.current.screenHeightDp * 0.1).dp,
    routes: List<CircularBarRoute>,
    state: CircularBottomAppBarState = rememberCircularAppBarState(
        routes = routes,
        selectedRoute = selectedRoute,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor
    )
) {
    val interactionSource = remember { MutableInteractionSource() }
    require(routes.distinct() == routes) { "You need to define different routes in BottomAppBar" }
    require(routes.size in 2..5) { "Invalid amount of routes" }
    require(routes.contains(selectedRoute)) { "BottomNavBar do not contain chosen route" }
    LaunchedEffect(key1 = routes, key2 = selectedRoute) {
        state.selectRoute(this, routes.indexOf(selectedRoute))
    }
    Layout(
        modifier = Modifier
            .clip(bottomAppBarShape)
            .background(containerColor)
            .windowInsetsPadding(windowInsets),
        content = {
            val colors = state.routeContentColors
            routes.fastForEachIndexed { i, it ->
                Column(
                    modifier = Modifier
                        .layoutId(MultiRoute.Route)
                        .clickable(
                            interactionSource = interactionSource, indication = null
                        ) {
                            it.onNavigateToRoute()
                        },
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Center
                ) {
                    Icon(
                        imageVector = it.icon, contentDescription = it.name, tint = colors[i]
                    )
                    Text(
                        text = it.name,
                        color = colors[i],
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Box(
                    modifier = Modifier
                        .height(navBarHeight)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(100))
                        .layoutId(MultiRoute.Background)
                        .background(selectorColor)
                        .rotate(
                            state.selectedRouteIndex / routes.size * 360
                        ),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .height(navBarHeight / 10)
                            .aspectRatio(1f)
                            .background(Color.White)
                    )
                }
            }
        }) { measurables, constraints ->
        val routeWidth = constraints.maxWidth / routes.size
        val routeConstraints = Constraints.fixed(
            width = routeWidth,
            height = navBarHeight.roundToPx()
        )
        val selectorConstrains = Constraints.fixed(
            width = navBarHeight.roundToPx(),
            height = navBarHeight.roundToPx()
        )
        val routesToPlace = measurables
            .filter { it.layoutId == MultiRoute.Route }
            .map { it.measure(routeConstraints) }
        val selector = measurables
            .first { it.layoutId == MultiRoute.Background }
            .measure(selectorConstrains)

        layout(
            width = constraints.maxWidth,
            height = navBarHeight.roundToPx()
        ) {
            selector.placeRelative(
                x = (state.selectedRouteIndex * routeWidth + routeWidth * 0.5 - selector.width / 2)
                    .toInt(),
                y = 0
            )
            routesToPlace.fastForEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = index * routeWidth,
                    y = 0
                )
            }
        }
    }
}
