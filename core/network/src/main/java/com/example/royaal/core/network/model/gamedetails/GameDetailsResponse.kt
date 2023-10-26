package com.example.royaal.core.network.model.gamedetails


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameDetailsResponse(
    @SerialName("achievements_count")
    val achievementsCount: Int,
    @SerialName("added")
    val added: Int,
    @SerialName("additions_count")
    val additionsCount: Int,
    @SerialName("alternative_names")
    val alternativeNames: List<String>,
    @SerialName("background_image")
    val backgroundImage: String,
    @SerialName("background_image_additional")
    val backgroundImageAdditional: String,
    @SerialName("description")
    val description: String,
    @SerialName("game_series_count")
    val gameSeriesCount: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("name_original")
    val nameOriginal: String,
    @SerialName("parents_count")
    val parentsCount: Int,
    @SerialName("platforms")
    val platforms: List<Platform>,
    @SerialName("playtime")
    val playtime: Int,
    @SerialName("rating")
    val rating: Float,
    @SerialName("rating_top")
    val ratingTop: Float,
    @SerialName("ratings_count")
    val ratingsCount: Int,
    @SerialName("reddit_count")
    val redditCount: Int,
    @SerialName("reddit_description")
    val redditDescription: String,
    @SerialName("reddit_logo")
    val redditLogo: String,
    @SerialName("reddit_name")
    val redditName: String,
    @SerialName("reddit_url")
    val redditUrl: String,
    @SerialName("released")
    val released: String,
    @SerialName("screenshots_count")
    val screenshotsCount: Int,
    @SerialName("slug")
    val slug: String,
    @SerialName("tba")
    val tba: Boolean,
    @SerialName("updated")
    val updated: String,
    @SerialName("website")
    val website: String,
)