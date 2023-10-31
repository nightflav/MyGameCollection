package com.example.royaal.api

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.domain.LocalGamesRepository

interface LocalGamesRepositoryProvider {

    val localGamesRepository: LocalGamesRepository
}

val LocalLocalGamesRepositoryProvider = compositionLocalOf<LocalGamesRepositoryProvider> {
    error("No GameDetailsRepo provided")
}