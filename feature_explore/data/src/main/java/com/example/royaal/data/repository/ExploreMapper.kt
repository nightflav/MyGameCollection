package com.example.royaal.data.repository

import com.example.royaal.core.common.model.uimodel.Developer
import com.example.royaal.core.common.model.uimodel.Genre
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.common.model.GameResult
import com.example.royaal.core.network.common.model.developers.Result
import com.example.royaal.core.network.common.model.genres.GenresResult
import com.example.royaal.core.network.common.model.platforms.PlatformsResult

val GameResult.previewGameModel
    get() = PreviewGameModel(
        name = name, id = id, posterUrl = backgroundImage!!
    )

val PlatformsResult.platform
    get() = Platform(
        id = id,
        name = name,
        backgroundImg = imageBackground
    )

val Result.developer
    get() = Developer(
        id = id,
        name = name,
        gamesCount = gamesCount,
        img = imageBackground
    )

val GenresResult.genre
    get() = Genre(
        id = id,
        name = name,
        imageBackground = imageBackground
    )