package com.example.royaal.data.repository

import com.example.royaal.core.common.Exceptions
import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.Screenshot
import com.example.royaal.core.database.app.GamesDao
import com.example.royaal.core.network.GamesApi
import com.example.royaal.domain.GameDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GameDetailRepositoryImpl @Inject constructor(
    private val gamesDao: GamesDao,
    private val gamesApi: GamesApi
) : GameDetailsRepository {

    override fun getGameById(id: Int): Flow<DetailsGameModel> = flow {
        if (gamesDao.isGameIsExist(id)) {
            val game = gamesDao.getGameById(id).detailedGameModel
            emit(game)
        } else {
            val response = gamesApi.getGameDetails(id)
            if (response.isSuccessful) {
                val screenshots = gamesApi.getGameScreenshots(id.toString())
                val game = response.body()!!
                    .toDetailedGameModel(
                        screenshots.body()?.results?.map { Screenshot(it.image) }
                            ?: throw Exceptions.networkException
                    )
                emit(game)
            } else {
                throw Exceptions.networkException
            }
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
        if (gamesDao.isGameIsExist(game.id)) {
            val gameDb = gamesDao.getGameById(game.id)
            gamesDao.updateGameById(
                gameDb.copy(
                    isFavourite = true
                )
            )
        } else {
            gamesDao.addGame(game.toGameEntity(isFavourite = true))
        }
    }

    override suspend fun addGameToCompleted(game: DetailsGameModel) {
        if (gamesDao.isGameIsExist(game.id)) {
            val gameDb = gamesDao.getGameById(game.id)
            gamesDao.updateGameById(
                gameDb.copy(
                    isCompleted = true
                )
            )
        } else {
            gamesDao.addGame(game.toGameEntity(isCompleted = true))
        }
    }

    override suspend fun addGameToWishlist(game: DetailsGameModel) {
        if (gamesDao.isGameIsExist(game.id)) {
            val gameDb = gamesDao.getGameById(game.id)
            gamesDao.updateGameById(
                gameDb.copy(
                    isInWishList = true
                )
            )
        } else {
            gamesDao.addGame(game.toGameEntity(isInWishList = true))
        }
    }

    override suspend fun removeGameFromFavourite(id: Int) {
        if (gamesDao.isGameIsExist(id)) {
            val game = gamesDao.getGameById(id)
            if (game.isCompleted || game.isInWishList) {
                gamesDao.updateGameById(
                    game.copy(
                        isFavourite = false
                    )
                )
            } else {
                gamesDao.deleteGame(game)
            }
        }
    }

    override suspend fun removeGameFromCompleted(id: Int) {
        if (gamesDao.isGameIsExist(id)) {
            val game = gamesDao.getGameById(id)
            if (game.isFavourite || game.isInWishList) {
                gamesDao.updateGameById(
                    game.copy(
                        isCompleted = false
                    )
                )
            } else {
                gamesDao.deleteGame(game)
            }
        }
    }

    override suspend fun removeGameFromWishlist(id: Int) {
        if (gamesDao.isGameIsExist(id)) {
            val game = gamesDao.getGameById(id)
            if (game.isCompleted || game.isFavourite) {
                gamesDao.updateGameById(
                    game.copy(
                        isInWishList = false
                    )
                )
            } else {
                gamesDao.deleteGame(game)
            }
        }
    }
}