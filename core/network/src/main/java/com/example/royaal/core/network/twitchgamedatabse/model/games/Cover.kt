package com.example.royaal.core.network.twitchgamedatabse.model.games

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cover(
    @SerialName("url")
    val url: String?,
    @SerialName("id")
    val id: Int
)