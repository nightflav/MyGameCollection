package com.example.royaal.core.network.common.model.screenshot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ScreenshotResult(
    @SerialName("image")
    val image: String
)