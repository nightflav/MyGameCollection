package com.example.royaal.feature_home

import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.network.GamesApi
import com.example.royaal.feature_home.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gamesApi: GamesApi,
) : GamesRepository {

    private val simpleCache: MutableMap<String, List<PreviewGameModel>> = mutableMapOf()

    override fun getLatestReleases(shouldFetch: Boolean): Flow<List<PreviewGameModel>> = flow {
        val key = "Latest"
        val toEmit = if (simpleCache[key].isNullOrEmpty()) {
            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val monthBefore = LocalDate.now().minusMonths(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE)
            val dates = "$monthBefore,$today"
            val response = gamesApi.getGamesByDate(fromTo = dates, ordering = "-dates")
            val data = if (response.isSuccessful) {
                response.body()!!.results.filter { it.backgroundImage != null }.toGameDtoList()
            } else {
                throw Exception("Unable to fetch the data from network.")
            }
            simpleCache[key] = data
            data
        } else {
            simpleCache[key]!!
        }
        emit(toEmit)
    }

    override fun getMostRatedLastMonth(shouldFetch: Boolean): Flow<List<PreviewGameModel>> = flow {
        val key = "MostRated"
        val toEmit = if (simpleCache[key].isNullOrEmpty()) {
            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val monthBefore = LocalDate.now().minusMonths(2)
                .format(DateTimeFormatter.ISO_LOCAL_DATE)
            val dates = "$monthBefore,$today"
            val response = gamesApi.getGamesByDate(fromTo = dates, ordering = "-dates")
            val data = if (response.isSuccessful) {
                response.body()!!.results.filter { it.backgroundImage != null }.toGameDtoList()
            } else {
                throw Exception("Unable to fetch the data from network.")
            }
            simpleCache[key] = data
            data
        } else {
            simpleCache[key]!!
        }
        emit(toEmit)
    }

    override fun getUpcomingReleases(shouldFetch: Boolean): Flow<List<PreviewGameModel>> = flow {
        val key = "Upcoming"
        val toEmit = if (simpleCache[key].isNullOrEmpty()) {
            val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
            val monthAfter = LocalDate.now().plusMonths(1)
                .format(DateTimeFormatter.ISO_LOCAL_DATE)
            val dates = "$today,$monthAfter"
            val response = gamesApi.getGamesByDate(fromTo = dates, ordering = "-dates")
            val data = if (response.isSuccessful) {
                response.body()!!.results.filter { it.backgroundImage != null }.toGameDtoList()
            } else {
                throw Exception("Unable to fetch the data from network.")
            }
            simpleCache[key] = data
            data
        } else {
            simpleCache[key]!!
        }
        emit(toEmit)
    }

}