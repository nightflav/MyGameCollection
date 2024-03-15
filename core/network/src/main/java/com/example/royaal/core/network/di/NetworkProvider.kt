package com.example.royaal.core.network.di

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.core.network.common.GamesApi
import com.example.royaal.core.network.twitchgamedatabse.TwitchApi
import com.example.royaal.core.network.twitchgamedatabse.TwitchWrapperApi

interface NetworkProvider {

    val gamesApi: GamesApi

    val twitchApi: TwitchApi

    val twitchWrapperApi: TwitchWrapperApi

}

val LocalNetworkProvider = compositionLocalOf<NetworkProvider> {
    error("No api provided")
}