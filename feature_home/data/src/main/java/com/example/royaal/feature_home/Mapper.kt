package com.example.royaal.feature_home

import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.model.GameResult

internal fun GameResult.toGameDto() = PreviewGameModel(
    id, name, backgroundImage!!
)

internal fun List<GameResult>.toGameDtoList() = map { it.toGameDto() }
