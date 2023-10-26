package com.example.royaal.core.database.app

import kotlinx.serialization.Serializable

@Serializable
data class PlatformDb(
    val name: String,
    val id: Int,
)