package com.example.royaal.feature_home

import android.util.Log
import com.example.royaal.core.common.SimpleCache
import com.example.royaal.core.common.SimpleCacheImpl
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.twitchgamedatabse.TwitchApi
import com.example.royaal.feature_home.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TwitchGameRepositoryImpl @Inject constructor(
    private val gamesApi: TwitchApi
) : GamesRepository, SimpleCache<List<PreviewGameModel>> by SimpleCacheImpl() {

    override fun getLatestReleases(shouldFetch: Boolean): Flow<List<PreviewGameModel>> = flow {
        val key = "Latest"
        val toEmit = if (!checkIfCached(key)) {
            Log.d("TAGTAG", "start loading games")
            val games = gamesApi.getGames("fields cover.url,name,id;").body()!!.toGameDtoListTwitch()
            Log.d("TAGTAG", "games = $games")
            cacheData(key, games)
            Log.d("TAGTAG", "$games")
            getByKey(key)
        } else {
            getByKey(key)
        }
        emit(toEmit)
    }

    override fun getMostRatedLastMonth(shouldFetch: Boolean): Flow<List<PreviewGameModel>> = flow {
        val key = "Most Rated"
        val toEmit = if (!checkIfCached(key)) {
            val games = gamesApi.getGames("fields *;").body()!!.toGameDtoListTwitch()
            cacheData(key, games)
            Log.d("TAGTAG", "$games")
            getByKey(key)
        } else {
            getByKey(key)
        }
        emit(toEmit)
    }

    override fun getUpcomingReleases(shouldFetch: Boolean): Flow<List<PreviewGameModel>> = flow {
        val key = "Upcoming"
        val toEmit = if (!checkIfCached(key)) {
            val games = gamesApi.getGames("fields *;").body()!!.toGameDtoListTwitch()
            cacheData(key, games)
            Log.d("TAGTAG", "$games")
            getByKey(key)
        } else {
            getByKey(key)
        }
        emit(toEmit)
        emit(toEmit)
    }
}