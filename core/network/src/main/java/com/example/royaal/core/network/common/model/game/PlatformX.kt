package com.example.royaal.core.network.common.model.game

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformX(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("slug")
    val slug: String
)