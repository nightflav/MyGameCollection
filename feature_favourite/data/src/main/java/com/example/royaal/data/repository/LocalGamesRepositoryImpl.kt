package com.example.royaal.data.repository

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.database.app.GamesDao
import com.example.royaal.core.database.app.detailedGameModel
import com.example.royaal.domain.LocalGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalGamesRepositoryImpl @Inject constructor(
    private val gamesDao: GamesDao
) : LocalGamesRepository {

    override fun getFavouriteGames(): Flow<List<DetailsGameModel>> = gamesDao.getFavouriteGames()
        .map { it -> it.map { it.detailedGameModel } }

    override fun getCompletedGames(): Flow<List<DetailsGameModel>> = gamesDao.getCompletedGames()
        .map { list -> list.map { it.detailedGameModel } }

    override fun getWishListGames(): Flow<List<DetailsGameModel>> = gamesDao.getWishListGames()
        .map { list -> list.map { it.detailedGameModel } }

}