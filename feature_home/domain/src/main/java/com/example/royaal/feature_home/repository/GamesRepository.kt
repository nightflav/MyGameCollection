package com.example.royaal.feature_home.repository

import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import kotlinx.coroutines.flow.Flow

interface GamesRepository {

    fun getLatestReleases(shouldFetch: Boolean): Flow<List<PreviewGameModel>>

    fun getMostRatedLastMonth(shouldFetch: Boolean): Flow<List<PreviewGameModel>>

    fun getUpcomingReleases(shouldFetch: Boolean): Flow<List<PreviewGameModel>>

}