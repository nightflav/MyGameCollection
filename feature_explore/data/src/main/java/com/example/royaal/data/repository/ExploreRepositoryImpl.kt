package com.example.royaal.data.repository

import com.example.royaal.core.common.isSearchRequest
import com.example.royaal.core.common.model.uimodel.Developer
import com.example.royaal.core.common.model.uimodel.Genre
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.common.GamesApi
import com.example.royaal.core.network.common.model.game.GameNetworkResponse
import com.example.royaal.core.network.common.model.platforms.PlatformsNetworkResponse
import com.example.royaal.domain.ExploreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.time.LocalDate
import javax.inject.Inject

class ExploreRepositoryImpl @Inject constructor(
    private val gamesApi: GamesApi
) : ExploreRepository {

    override fun getUpcomingGames(): Flow<List<PreviewGameModel>> = flow {
        val now = LocalDate.now()
        val afterWeek = now.plusWeeks(1)
        val interval = "$now,$afterWeek"
        gamesApi.getGamesByDate(fromTo = interval, ordering = "-released")
            .emitGamesOnSuccess(this)
    }

    override fun getLatestReleases(): Flow<List<PreviewGameModel>> = flow {
        val now = LocalDate.now()
        val beforeMonth = now.minusMonths(1)
        val interval = "$beforeMonth,$now"
        gamesApi.getGamesByDate(fromTo = interval, ordering = "-rating")
            .emitGamesOnSuccess(this)
    }

    override fun getByPlatform(platform: Int, page: Int): Flow<List<PreviewGameModel>> = flow {
        gamesApi.getGamesByGenreAndPlatform(
            platform = platform.toString(),
            page = page,
            ordering = "rating"
        ).emitGamesOnSuccess(this)
    }

    override fun searchForGames(query: String): Flow<List<PreviewGameModel>> = flow {
        gamesApi.searchForGames(search = query)
            .emitGamesOnSuccess(this)
    }

    override fun searchForPlatform(query: String): Flow<List<Platform>> = flow {
        gamesApi.getPlatforms()
            .emitPlatformsOnSuccess(this, query)
    }

    override fun searchForDevelopers(query: String): Flow<List<Developer>> = flow {
        val response = gamesApi.getDevelopers()
        if (response.isSuccessful)
            emit(response.body()!!.results.filter { it.name.isSearchRequest(query) }
                .map { it.developer })

    }

    override fun getGenres(): Flow<List<Genre>> = flow {
        val response = gamesApi.getGenres()
        if (response.isSuccessful)
            emit(response.body()!!.results.map { it.genre })
    }

    override fun getPlatforms(): Flow<List<Platform>> = flow {
        gamesApi.getPlatforms().emitPlatformsOnSuccess(this)
    }

    private suspend fun Response<PlatformsNetworkResponse>.emitPlatformsOnSuccess(
        emitTo: FlowCollector<List<Platform>>,
        query: String = ""
    ) {
        if (isSuccessful)
            emitTo.emit(
                body()!!.results
                    .filter { it.name.isSearchRequest(query) }.map {
                        it.platform
                    }
            )
    }

    private suspend fun Response<GameNetworkResponse>.emitGamesOnSuccess(emitTo: FlowCollector<List<PreviewGameModel>>) {
        if (isSuccessful)
            emitTo.emit(
                body()!!.results.filter { it.backgroundImage != null }.map {
                    it.previewGameModel
                }
            )
    }
}