package com.example.royaal.domain

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import kotlinx.coroutines.flow.Flow

interface GameDetailsRepository {

    fun getGameById(id: Int): Flow<DetailsGameModel>

    fun getGamesFromSameSeries(id: Int): Flow<List<PreviewGameModel>>

    suspend fun addGameToFavourite(game: DetailsGameModel)

    suspend fun addGameToCompleted(game: DetailsGameModel)

    suspend fun addGameToWishlist(game: DetailsGameModel)

    suspend fun removeGameFromFavourite(id: Int)

    suspend fun removeGameFromCompleted(id: Int)

    suspend fun removeGameFromWishlist(id: Int)


}