package com.example.royaal.commonui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingPlaceholder() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_games_animation)
    )
    val infiniteTransition = rememberInfiniteTransition(label = "1")
    val progress by infiniteTransition.animateFloat(
        label = "2",
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .height(600.dp)
            .width(400.dp)
    )
}