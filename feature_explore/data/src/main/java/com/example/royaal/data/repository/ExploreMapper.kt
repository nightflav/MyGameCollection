package com.example.royaal.data.repository

import com.example.royaal.core.common.model.Developer
import com.example.royaal.core.common.model.uimodel.Genre
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.model.GameResult
import com.example.royaal.core.network.model.developers.Result
import com.example.royaal.core.network.model.genres.GenresResult
import com.example.royaal.core.network.model.platforms.PlatformsResult

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
        gamesCount = gamesCount
    )

val GenresResult.genre
    get() = Genre(
        id = id,
        name = name,
        imageBackground = imageBackground
    )