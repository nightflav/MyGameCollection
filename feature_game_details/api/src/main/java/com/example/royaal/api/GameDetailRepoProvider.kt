package com.example.royaal.api

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.domain.GameDetailsRepository

interface GameDetailsRepoProvider {

    val gameDetailsRepository: GameDetailsRepository
}

val LocalGameDetailsProvider = compositionLocalOf<GameDetailsRepoProvider> {
    error("No GameDetailsRepo provided")
}