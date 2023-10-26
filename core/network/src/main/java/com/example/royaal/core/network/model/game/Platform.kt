package com.example.royaal.core.network.model.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Platform(
    @SerialName("platform")
    val platform: PlatformX,
    @SerialName("released_at")
    val releasedAt: String = "unknown",
    @SerialName("requirements")
    val requirements: Requirements? = null
)