package com.example.royaal.core.network.twitchgamedatabse.auth

import com.example.royaal.core.network.twitchgamedatabse.model.tokens.TwitchTokenResponse
import com.example.royaal.core.network.twitchgamedatabse.model.tokens.TwitchValidateTokensResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TwitchAuth {

    @POST("oauth2/token")
    suspend fun refreshTokens(
        @Query("client_id")
        clientId: String,
        @Query("client_secret")
        clientSecret: String,
        @Query("grant_type")
        grantType: String = "client_credentials"
    ): Response<TwitchTokenResponse>

    @GET("oauth2/validate")
    suspend fun validateTokens(
        @Header("Authorization")
        auth: String
    ): Response<TwitchValidateTokensResponse>
}