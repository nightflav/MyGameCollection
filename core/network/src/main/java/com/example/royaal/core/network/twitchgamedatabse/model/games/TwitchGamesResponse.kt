package com.example.royaal.core.network.twitchgamedatabse.model.games


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitchGamesResponse(
    @SerialName("age_ratings")
    val ageRatings: List<Int>?,
    @SerialName("artworks")
    val artworks: List<Int>?,
    @SerialName("category")
    val category: Int,
    @SerialName("checksum")
    val checksum: String,
    @SerialName("cover")
    val cover: Cover,
    @SerialName("created_at")
    val createdAt: Int,
    @SerialName("external_games")
    val externalGames: List<Int>?,
    @SerialName("first_release_date")
    val firstReleaseDate: Int?,
    @SerialName("follows")
    val follows: Int?,
    @SerialName("game_modes")
    val gameModes: List<Int>?,
    @SerialName("genres")
    val genres: List<Int>?,
    @SerialName("hypes")
    val hypes: Int?,
    @SerialName("id")
    val id: Int,
    @SerialName("involved_companies")
    val involvedCompanies: List<Int>?,
    @SerialName("keywords")
    val keywords: List<Int>?,
    @SerialName("language_supports")
    val languageSupports: List<Int>?,
    @SerialName("multiplayer_modes")
    val multiplayerModes: List<Int>?,
    @SerialName("name")
    val name: String,
    @SerialName("platforms")
    val platforms: List<Int>?,
    @SerialName("release_dates")
    val releaseDates: List<Int>?,
    @SerialName("screenshots")
    val screenshots: List<Int>?,
    @SerialName("similar_games")
    val similarGames: List<Int>?,
    @SerialName("slug")
    val slug: String,
    @SerialName("status")
    val status: Int?,
    @SerialName("summary")
    val summary: String?,
    @SerialName("tags")
    val tags: List<Int>?,
    @SerialName("themes")
    val themes: List<Int>?,
    @SerialName("updated_at")
    val updatedAt: Int,
    @SerialName("url")
    val url: String,
    @SerialName("version_parent")
    val versionParent: Int?,
    @SerialName("version_title")
    val versionTitle: String?,
    @SerialName("videos")
    val videos: List<Int>?,
    @SerialName("websites")
    val websites: List<Int>?
)