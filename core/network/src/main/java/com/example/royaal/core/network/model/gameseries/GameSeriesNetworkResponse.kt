package com.example.royaal.core.network.model.gameseries

import com.example.royaal.core.network.model.GameResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameSeriesNetworkResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val results: List<GameResult>
)