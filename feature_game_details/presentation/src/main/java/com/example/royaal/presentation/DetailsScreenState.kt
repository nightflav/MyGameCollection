package com.example.royaal.presentation

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.Screenshot

data class DetailsScreenState(
    val gameId: Int,
    val title: String,
    val backgroundImg: String,
    val description: String,
    val screenshots: List<Screenshot>,
    val platforms: List<Platform>,
    val similarGames: List<PreviewGameModel>,
    val isFavourite: Boolean,
    val isCompleted: Boolean,
    val isInWishList: Boolean,
    val isLoading: Boolean = true,
    val error: Throwable? = null,
) {
    companion object {
        val EmptyState = DetailsScreenState(
            gameId = -1,
            title = "",
            backgroundImg = "",
            description = "",
            screenshots = emptyList(),
            platforms = emptyList(),
            similarGames = emptyList(),
            isFavourite = false,
            isCompleted = false,
            isInWishList = false
        )

        val DetailsScreenState.game
            get() = DetailsGameModel(
                id = gameId,
                description = description,
                name = title,
                backgroundUrl = backgroundImg,
                platforms = platforms.map { Platform(it.id, it.name) },
                screenshots = screenshots
            )
    }
}