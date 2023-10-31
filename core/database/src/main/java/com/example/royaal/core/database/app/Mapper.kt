package com.example.royaal.core.database.app

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.Screenshot

val GameEntity.detailedGameModel
    get() = DetailsGameModel(
        id = id,
        name = name,
        backgroundUrl = backgroundUrl,
        platforms = platforms.map { Platform(it.id, it.name) },
        screenshots = screenshots.map { Screenshot(it) },
        description = description,
        isFavourite = isFavourite,
        isCompleted = isCompleted,
        isInWishlist = isInWishList
    )

fun DetailsGameModel.toGameEntity(
    isFavourite: Boolean = false,
    isCompleted: Boolean = false,
    isInWishList: Boolean = false
): GameEntity =
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