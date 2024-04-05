package com.example.royaal.commonui.shimmers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRow(
    color: Color = Color.LightGray,
    shape: Shape = RoundedCornerShape(24.dp),
    height: Dp = 200.dp,
    width: Dp = 200.dp,
    contentPadding: PaddingValues = PaddingValues(),
    spaceBetween: Dp = 0.dp
) {
    LazyRow(
        modifier = Modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        (1..3).forEach { _ ->
            item {
                Box(
                    modifier = Modifier
                        .clip(shape)
                        .background(ShimmerDefaults.getBrush(color))
                        .height(height)
                        .width(width)
                )
            }
        }
    }
}