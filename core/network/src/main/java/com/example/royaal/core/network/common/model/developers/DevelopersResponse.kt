package com.example.royaal.core.network.common.model.developers


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DevelopersResponse(
    @SerialName("count")
    val count: Int,
    @SerialName("results")
    val results: List<Result>
)