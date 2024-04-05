package com.example.royaal.core.network.twitchgamedatabse.model.tokens


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitchTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("expires_in")
    val expiresIn: Int,
    @SerialName("token_type")
    val tokenType: String
)