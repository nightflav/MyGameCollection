package com.example.royaal.core.network.common.model.platforms

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlatformsNetworkResponse(
    @SerialName("count")
    val count: Int?,
    @SerialName("next")
    val next: String?,
    @SerialName("previous")
    val previous: String?,
    @SerialName("results")
    val results: List<PlatformsResult>
)