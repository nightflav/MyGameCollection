package com.example.royaal.commonui.multipleselector

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import com.example.royaal.commonui.CardConst
import com.example.royaal.commonui.DimConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private sealed interface MultiOption {
    data object Option : MultiOption
    data object Background : MultiOption
}

@Stable
interface MultipleSelectorState {

    val selectedOptionIndex: Float
    val textColors: List<Color>

    fun selectOption(scope: CoroutineScope, newIndex: Int)
}

@Stable
class MultiSelectorStateImpl(
    options: List<String>,
    selectedOption: String,
    private val selectedColor: Color,
    private val unselectedColor: Color,
) : MultipleSelectorState {
    override val selectedOptionIndex: Float
        get() = _selectedIndex.value

    override val textColors: List<Color>
        get() = _textColors.value

    private var _selectedIndex = Animatable(options.indexOf(selectedOption).toFloat())

    private var _textColors: State<List<Color>> = derivedStateOf {
        List(options.size) { index ->
            lerp(
                start = unselectedColor,
                stop = selectedColor,
                fraction = 1f - (selectedOptionIndex - index.toFloat())
                    .absoluteValue
                    .coerceAtMost(1f)
            )
        }
    }

    private val animationSpec = tween<Float>(
        durationMillis = 1000,
        easing = FastOutSlowInEasing,
    )

    override fun selectOption(scope: CoroutineScope, newIndex: Int) {
        scope.launch {
            _selectedIndex.animateTo(
                targetValue = newIndex.toFloat(),
                animationSpec = animationSpec,
            )
        }
    }
}

@Composable
fun rememberMultipleSelectorState(
    options: List<String>,
    selectedOption: String,
    selectedColor: Color,
    unselectedColor: Color
) = remember {
    MultiSelectorStateImpl(
        options,
        selectedOption,
        selectedColor,
        unselectedColor
    )
}

@Composable
fun MultipleSelector(
    options: List<String>,
    selectedOption: String,
    onOptionSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    selectedColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    unselectedColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    state: MultipleSelectorState = rememberMultipleSelectorState(
        options = options,
        selectedOption = selectedOption,
        selectedColor = selectedColor,
        unselectedColor = unselectedColor
    )
) {
    val interactionSource = remember { MutableInteractionSource() }
    require(options.size >= 2)
    require(options.size <= 5)
    require(options.contains(selectedOption))
    LaunchedEffect(key1 = options, key2 = selectedOption) {
        state.selectOption(this, options.indexOf(selectedOption))
    }
    Layout(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(percent = CardConst.halfCornerRadiusPercent)
            )
            .background(MaterialTheme.colorScheme.onSurface),
        content = {
            val colors = state.textColors
            options.forEachIndexed { index, option ->
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onOptionSelect(option)
                        }
                        .layoutId(MultiOption.Option),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors[index],
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = DimConst.smallPadding),
                    )
                }
            }
            Box(
                modifier = Modifier
                    .layoutId(MultiOption.Background)
                    .background(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(CardConst.halfCornerRadiusPercent)
                    )
            )
        }
    ) { measurables, constraints ->
        val optionWidth = constraints.maxWidth / options.size
        val optionConstraints = Constraints.fixed(
            width = optionWidth,
            height = constraints.maxHeight,
        )
        val optionsToPlace = measurables
            .filter { it.layoutId == MultiOption.Option }
            .map { it.measure(optionConstraints) }
        val background = measurables
            .first { it.layoutId == MultiOption.Background }
            .measure(optionConstraints)

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight,
        ) {
            background.placeRelative(
                x = (state.selectedOptionIndex * optionWidth).toInt(), 0
            )
            optionsToPlace.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = optionWidth * index,
                    y = 0,
                )
            }
        }
    }
}