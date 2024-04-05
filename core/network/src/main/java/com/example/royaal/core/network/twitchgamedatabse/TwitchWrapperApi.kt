package com.example.royaal.core.network.twitchgamedatabse

import com.example.royaal.core.network.twitchgamedatabse.model.wrapper.TwitchGame

interface TwitchWrapperApi {

    fun getLatestReleases(): List<TwitchGame>

    fun getUpcomingReleases(): List<TwitchGame>

    fun getMostRatedReleases(): List<TwitchGame>
}