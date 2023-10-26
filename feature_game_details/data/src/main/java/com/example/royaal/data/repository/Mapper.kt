package com.example.royaal.data.repository

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.Screenshot
import com.example.royaal.core.database.app.GameEntity
import com.example.royaal.core.database.app.PlatformDb
import com.example.royaal.core.network.model.GameResult
import com.example.royaal.core.network.model.gamedetails.GameDetailsResponse

val GameEntity.detailedGameModel
    get() = DetailsGameModel(
        id = id,
        name = name,
        backgroundUrl = backgroundUrl,
        platforms = platforms.map { Platform(it.id, it.name) },
        screenshots = screenshots.map { Screenshot(it) },
        description = description,
    )

fun GameDetailsResponse.toDetailedGameModel(screenshots: List<Screenshot>) = DetailsGameModel(
    id = id,
    name = name,
    backgroundUrl = backgroundImage,
    platforms = platforms.map {
        val p = it.platform
        Platform(
            p.id, p.name
        )
    },
    description = description,
    screenshots = screenshots
)

val GameResult.previewGameModel
    get() = PreviewGameModel(
        id = id,
        name = name,
        posterUrl = backgroundImage!!
    )

fun DetailsGameModel.toGameEntity(
    isFavourite: Boolean = false,
    isCompleted: Boolean = false,
    isInWishList: Boolean = false
): GameEntity =
    if (isFavourite || isCompleted || isInWishList) {
        GameEntity(
            id = id,
            name = name,
            platforms = platforms.map { PlatformDb(it.name, it.id) },
            backgroundUrl = backgroundUrl,
            description = description,
            screenshots = screenshots.map { it.url },
            isInWishList = isInWishList,
            isFavourite = isFavourite,
            isCompleted = isCompleted
        )
    } else {
        throw Exception("Game should be at least in one of all lists")
    }