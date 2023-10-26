package com.example.royaal.core.network.model.gamedetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Platform(
    @SerialName("platform")
    val platform: PlatformX,
    @SerialName("released_at")
    val releasedAt: String,
)