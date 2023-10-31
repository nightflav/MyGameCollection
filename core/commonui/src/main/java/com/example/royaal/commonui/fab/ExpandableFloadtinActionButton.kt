package com.example.royaal.commonui.fab

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private sealed interface FabOption {
    data object MainButton : FabOption
    data object OptionButton : FabOption
}

@Stable
interface ExpandableFabState {
    val isExpanded: Boolean

    val expansionState: Float

    fun expand(scope: CoroutineScope)

    fun collapse(scope: CoroutineScope)
}

@Stable
class ExpandableFabStateImpl(
    isExpanded: Boolean,
    duration: Int
) : ExpandableFabState {
    override val isExpanded: Boolean
        get() = (_isExpanded.value > 0)

    override val expansionState: Float
        get() = _isExpanded.value

    private val _isExpanded = Animatable(if (isExpanded) 1f else 0f)

    private val animationSpec = tween<Float>(
        durationMillis = duration,
        easing = FastOutSlowInEasing,
    )

    override fun expand(scope: CoroutineScope) {
        scope.launch {
            _isExpanded.animateTo(
                1f, animationSpec
            )
        }
    }

    override fun collapse(scope: CoroutineScope) {
        scope.launch {
            _isExpanded.animateTo(
                0f, animationSpec
            )
        }
    }

}

@Composable
fun rememberExpandableFabState(
    isExpanded: Boolean,
    duration: Int
) = remember {
    ExpandableFabStateImpl(
        isExpanded,
        duration
    )
}

class ExpandedFabOption(
    val name: String,
    val icon: ImageVector,
    val shape: Shape? = null,
    val onOptionSelected: (ExpandedFabOption) -> Unit,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpandableFloatingActionButton(
    modifier: Modifier = Modifier,
    expandedColor: Color,
    mainButtonColor: Color,
    expansionDuration: Int,
    state: ExpandableFabState = rememberExpandableFabState(
        isExpanded = false,
        duration = expansionDuration
    ),
    options: List<ExpandedFabOption>,
    icon: ImageVector,
    shape: Shape = RoundedCornerShape(50),
    optionRoundedCorners: Dp = 16.dp,
    mainButtonWidth: Dp,
    mainButtonHeight: Dp,
    needToRotate: Boolean = false,
    onClick: (ExpandableFabState, CoroutineScope) -> Unit = { fabState, scope ->
        if (fabState.isExpanded) fabState.collapse(scope)
        else fabState.expand(scope)
    },
    spaceBetweenOptions: Dp = 0.dp
) {
    val fabScope = rememberCoroutineScope()
    require(options.isNotEmpty()) { "Not enough options" }
    require(options.size <= 3) { "Too much options" }
    Layout(
        modifier = modifier,
        content = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(optionRoundedCorners))
                    .background(expandedColor)
                    .layoutId(FabOption.OptionButton)
            )
            options.forEachIndexed { i, option ->
                Box(
                    modifier = Modifier
                        .clip(
                            shape = option.shape ?: if (i == options.size - 1) {
                                RoundedCornerShape(
                                    topStart = (50 * state.expansionState).dp + optionRoundedCorners,
                                    topEnd = (50 * state.expansionState).dp + optionRoundedCorners,
                                    bottomEnd = optionRoundedCorners,
                                    bottomStart = optionRoundedCorners
                                )
                            } else {
                                RoundedCornerShape(optionRoundedCorners)
                            }
                        )
                        .background(expandedColor)
                        .layoutId(FabOption.OptionButton)
                        .clickable { option.onOptionSelected(option) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = null,
                    )
                }
            }
            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(mainButtonColor)
                    .layoutId(FabOption.MainButton)
                    .combinedClickable(
                        onClick = {
                            onClick(state, fabScope)
                        },
                        onLongClick = {
                            if (state.isExpanded)
                                state.collapse(fabScope)
                            else
                                state.expand(fabScope)
                        },
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.rotate(if (needToRotate) state.expansionState * 180 else 0f)
                )
            }
        },
    ) { measurables, _ ->
        val mainButtonWidthPx = mainButtonWidth.roundToPx()
        val mainButtonHeightPx = mainButtonHeight.roundToPx()
        val optionHeight = (0.8 * mainButtonHeightPx).toInt()
        val optionWidth = (0.8 * mainButtonWidthPx).toInt()
        val optionConstraints = Constraints.fixed(
            width = optionWidth, height = optionHeight
        )
        val mainButtonConstraints = Constraints.fixed(
            width = mainButtonWidthPx, height = mainButtonHeightPx
        )
        val optionPlaceables =
            measurables.filter { it.layoutId == FabOption.OptionButton }.reversed()
                .map { measurable ->
                    measurable.measure(optionConstraints)
                }
        val mainButton =
            measurables.first { it.layoutId == FabOption.MainButton }.measure(mainButtonConstraints)
        val width = mainButtonWidth.roundToPx()
        val height =
            (if (state.isExpanded)
                (optionHeight * options.size + mainButtonHeight.roundToPx())
            else
                mainButtonHeight.roundToPx()) + spaceBetweenOptions.roundToPx() * options.size
        layout(
            width = width, height = height
        ) {
            if (state.isExpanded) {
                optionPlaceables.reversed().forEachIndexed { i, option ->
                    option.place(
                        x = ((mainButtonWidthPx - optionWidth) / 2),
                        y = (height - mainButtonHeightPx - i * state.expansionState * (optionHeight + spaceBetweenOptions.roundToPx())).toInt()
                    )
                }
            }
            mainButton.place(
                x = 0, y = height - mainButtonHeightPx
            )
        }
    }
}
