package com.example.royaal.api

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.domain.GameDetailsRepository

interface GameDetailsRepositoryProvider {

    val gameDetailsRepository: GameDetailsRepository
}

val LocalGameDetailsProvider = compositionLocalOf<GameDetailsRepositoryProvider> {
    error("No GameDetailsRepo provided")
}