package com.example.royaal.core.network.common

import com.example.royaal.core.network.common.model.developers.DevelopersResponse
import com.example.royaal.core.network.common.model.game.GameNetworkResponse
import com.example.royaal.core.network.common.model.gamedetails.GameDetailsResponse
import com.example.royaal.core.network.common.model.gameseries.GameSeriesNetworkResponse
import com.example.royaal.core.network.common.model.genres.GenresNetworkResponse
import com.example.royaal.core.network.common.model.platforms.PlatformsNetworkResponse
import com.example.royaal.core.network.common.model.screenshot.ScreenshotNetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET("games")
    suspend fun getGamesByGenreAndPlatform(
        @Query("genres") genre: String? = null,
        @Query("platforms") platform: String? = null,
        @Query("page") page: Int? = null,
        @Query("ordering") ordering: String? = null
    ): Response<GameNetworkResponse>

    @GET("games")
    suspend fun searchForGames(
        @Query("search") search: String,
        @Query("ordering") ordering: String? = null
    ): Response<GameNetworkResponse>

    @GET("games")
    suspend fun getGamesByDate(
        @Query("dates") fromTo: String,
        @Query("ordering") ordering: String? = null
    ): Response<GameNetworkResponse>

    @GET("games")
    suspend fun getGamesParametrised(
        @Query("page") page: String? = null,
        @Query("page_size") pageSize: String? = null,
        @Query("search") search: String? = null,
        @Query("platforms") platforms: String? = null,
        @Query("stores") stores: String? = null,
        @Query("developers") developers: String? = null,
        @Query("publishers") publishers: String? = null,
        @Query("genres") genres: String? = null,
        @Query("dates") dates: String? = null,
        @Query("metacritic") metacritic: String? = null,
        @Query("ordering") ordering: String? = null,
    ): Response<GameNetworkResponse>

    @GET("games/{game_pk}/screenshots")
    suspend fun getGameScreenshots(
        @Path("game_pk") gamePk: String
    ): Response<ScreenshotNetworkResponse>

    @GET("games/{game_pk}/game-series")
    suspend fun getGameSeries(
        @Path("game_pk") gamePk: String
    ): Response<GameSeriesNetworkResponse>

    @GET("genres")
    suspend fun getGenres(): Response<GenresNetworkResponse>

    @GET("platforms")
    suspend fun getPlatforms(): Response<PlatformsNetworkResponse>

    @GET("developers")
    suspend fun getDevelopers(): Response<DevelopersResponse>

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id") id: Int,
    ): Response<GameDetailsResponse>
}