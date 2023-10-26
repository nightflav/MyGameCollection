package com.example.royaal.core.network.model.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Requirements(
    @SerialName("minimum")
    val minimum: String,
    @SerialName("recommended")
    val recommended: String
)