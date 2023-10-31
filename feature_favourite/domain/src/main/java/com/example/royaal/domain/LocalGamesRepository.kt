package com.example.royaal.domain

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import kotlinx.coroutines.flow.Flow

interface LocalGamesRepository {

    fun getFavouriteGames(): Flow<List<DetailsGameModel>>

    fun getCompletedGames(): Flow<List<DetailsGameModel>>

    fun getWishListGames(): Flow<List<DetailsGameModel>>
}