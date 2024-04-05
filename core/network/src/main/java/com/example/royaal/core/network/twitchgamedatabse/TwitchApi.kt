package com.example.royaal.core.network.twitchgamedatabse

import com.example.royaal.core.network.twitchgamedatabse.model.games.TwitchGamesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TwitchApi {

    @POST("games")
    suspend fun getGames(
        @Body body: String
    ): Response<List<TwitchGamesResponse>>

}