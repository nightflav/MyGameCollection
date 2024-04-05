package com.example.royaal.core.network.twitchgamedatabse.model.tokens


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TwitchValidateTokensResponse(
    @SerialName("client_id")
    val clientId: String,
    @SerialName("expires_in")
    val expiresIn: Int,
)