package com.example.royaal.data.repository

import com.example.royaal.core.common.Exceptions
import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.Screenshot
import com.example.royaal.core.database.app.GamesDao
import com.example.royaal.core.database.app.detailedGameModel
import com.example.royaal.core.database.app.toGameEntity
import com.example.royaal.core.network.GamesApi
import com.example.royaal.domain.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GameDetailRepositoryImpl @Inject constructor(
    private val gamesDao: GamesDao,
    private val gamesApi: GamesApi
) : GameDetailsRepository {

    override fun getGameById(id: Int): Flow<DetailsGameModel> = flow {
        if (!gamesDao.isGameExist(id))
            loadGame(id)
        emitAll(gamesDao.getGameById(id).map { it.detailedGameModel })
    }

    private suspend fun loadGame(id: Int) {
        val gameResponse = gamesApi.getGameDetails(id)
        if (gameResponse.isSuccessful) {
            val game = gameResponse.body()!!
                .toDetailedGameModel(gamesApi.getGameScreenshots(id.toString())
                    .body()?.results?.map {
                        Screenshot(it.image)
                    } ?: emptyList()
                )
            gamesDao.addGame(game.toGameEntity())
        }
    }

    override fun getGamesFromSameSeries(id: Int): Flow<List<PreviewGameModel>> = flow {
        val response = gamesApi.getGameSeries(id.toString())
        if (response.isSuccessful) {
            val games = response.body()!!.results.filter { it.backgroundImage != null }.map {
                it.previewGameModel
            }
            emit(games)
        } else {
            throw Exceptions.networkException
        }
    }

    override suspend fun addGameToFavourite(game: DetailsGameModel) {
        if (gamesDao.isGameExist(game.id)) {
            gamesDao.updateFavouriteStatusById(game.id, true)
        } else {
            gamesDao.addGame(game.toGameEntity(isFavourite = true))
        }
    }

    override suspend fun addGameToCompleted(game: DetailsGameModel) {
        if (gamesDao.isGameExist(game.id)) {
            gamesDao.updateCompletedStatusById(game.id, true)
        } else {
            gamesDao.addGame(game.toGameEntity(isCompleted = true))
        }
    }

    override suspend fun addGameToWishlist(game: DetailsGameModel) {
        if (gamesDao.isGameExist(game.id)) {
            gamesDao.updateWishlistStatusById(game.id, true)
        } else {
            gamesDao.addGame(game.toGameEntity(isInWishList = true))
        }
    }

    override suspend fun removeGameFromFavourite(id: Int) {
        if (gamesDao.isGameExist(id)) gamesDao.updateFavouriteStatusById(id, false)

    }

    override suspend fun removeGameFromCompleted(id: Int) {
        if (gamesDao.isGameExist(id)) gamesDao.updateCompletedStatusById(id, false)
    }

    override suspend fun removeGameFromWishlist(id: Int) {
        if (gamesDao.isGameExist(id)) gamesDao.updateWishlistStatusById(id, false)
    }
}