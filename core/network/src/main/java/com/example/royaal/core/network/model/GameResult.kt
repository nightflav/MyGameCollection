package com.example.royaal.core.network.model

import com.example.royaal.core.network.model.game.Platform
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameResult(
    @SerialName("added")
    val added: Int,
    @SerialName("background_image")
    val backgroundImage: String?,
    @SerialName("id")
    val id: Int,
    @SerialName("metacritic")
    val metacritic: Int = -1,
    @SerialName("name")
    val name: String,
    @SerialName("platforms")
    val platforms: List<Platform> = emptyList(),
    @SerialName("playtime")
    val playtime: Int,
    @SerialName("rating")
    val rating: Float,
    @SerialName("rating_top")
    val ratingTop: Int,
    @SerialName("released")
    val released: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("suggestions_count")
    val suggestionsCount: Int,
    @SerialName("tba")
    val tba: Boolean,
    @SerialName("updated")
    val updated: String
)