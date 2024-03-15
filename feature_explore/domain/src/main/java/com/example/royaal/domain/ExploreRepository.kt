package com.example.royaal.domain

import com.example.royaal.core.common.model.uimodel.Developer
import com.example.royaal.core.common.model.uimodel.Genre
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {

    fun getUpcomingGames(): Flow<List<PreviewGameModel>>

    fun getLatestReleases(): Flow<List<PreviewGameModel>>

    fun getByPlatform(platform: Int, page: Int = 1): Flow<List<PreviewGameModel>>

    fun searchForGames(query: String): Flow<List<PreviewGameModel>>

    fun searchForPlatform(query: String): Flow<List<Platform>>

    fun searchForDevelopers(query: String): Flow<List<Developer>>

    fun getGenres(): Flow<List<Genre>>

    fun getPlatforms(): Flow<List<Platform>>

}