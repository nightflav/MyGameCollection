package com.example.royaal.core.common.model.uimodel

data class DetailsGameModel(
    val id: Int,
    val name: String,
    val backgroundUrl: String,
    val platforms: List<Platform>,
    val screenshots: List<Screenshot>,
    val description: String,
    val isFavourite: Boolean = false,
    val isInWishlist: Boolean = false,
    val isCompleted: Boolean = false
)