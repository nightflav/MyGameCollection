package com.example.royaal.commonui.shimmers

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object ShimmerDefaults {

    @Composable
    fun getBrush(color: Color): Brush {
        val shimmerColors = listOf(
            color.copy(alpha = 0.9f),
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.9f),
        )
        val transition = rememberInfiniteTransition(label = "ShimmerInfiniteTransition")
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1500f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 1500,
                    easing = FastOutLinearInEasing
                ),
                repeatMode = RepeatMode.Reverse
            ),
            label = "ShimmerAnimation"
        )
        return Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(0f, 0f),
            end = Offset(
                x = translateAnimation.value,
                y = translateAnimation.value
            )
        )
    }

}