package com.example.royaal.api

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.domain.PlatformDetailsRepository

interface PlatformDetailsRepositoryProvider {

    val gameDetailsRepository: PlatformDetailsRepository
}

val LocalPlatformDetailsProvider = compositionLocalOf<PlatformDetailsRepositoryProvider> {
    error("No PlatformDetailsRepo provided")
}