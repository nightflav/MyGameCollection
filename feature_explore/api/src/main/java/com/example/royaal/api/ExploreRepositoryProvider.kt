package com.example.royaal.api

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.domain.ExploreRepository

interface ExploreRepositoryProvider {

    val provideExploreRepository: ExploreRepository
}

val LocalExploreRepository =
    compositionLocalOf<ExploreRepositoryProvider> {
        error("No ExploreRepository provided")
    }