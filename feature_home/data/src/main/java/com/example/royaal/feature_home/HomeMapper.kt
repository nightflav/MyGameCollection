package com.example.royaal.feature_home

import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.common.model.GameResult
import com.example.royaal.core.network.twitchgamedatabse.model.games.TwitchGamesResponse
import java.text.SimpleDateFormat
import java.util.Locale

internal fun GameResult.toGameDto() = PreviewGameModel(
    id, name, backgroundImage!!
)

internal fun List<GameResult>.toGameDtoList() = map { it.toGameDto() }

internal fun TwitchGamesResponse.toGameDtoTwitch() = PreviewGameModel(
    id = id, name = name, posterUrl = cover.url!!,
    releaseDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(firstReleaseDate)
)

internal fun List<TwitchGamesResponse>.toGameDtoListTwitch() = map { it.toGameDtoTwitch() }