package com.example.royaal.data.repository

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.Screenshot
import com.example.royaal.core.network.common.model.GameResult
import com.example.royaal.core.network.common.model.gamedetails.GameDetailsResponse

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
