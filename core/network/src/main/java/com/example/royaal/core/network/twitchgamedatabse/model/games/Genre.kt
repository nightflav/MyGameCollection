package com.example.royaal.core.network.twitchgamedatabse.model.games

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("id")
    val id: Int,
    @SerialName("url")
    val url: String?
)
