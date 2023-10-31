package com.example.royaal.api

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.feature_home.repository.GamesRepository

interface GamesRepositoryProvider {

    val gamesRepo: GamesRepository
}

val LocalGamesRepositoryProvider = compositionLocalOf<GamesRepositoryProvider> {
    error("No GamesRepository provided")
}