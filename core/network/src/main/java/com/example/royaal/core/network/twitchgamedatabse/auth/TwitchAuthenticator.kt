package com.example.royaal.core.network.twitchgamedatabse.auth

import com.example.royaal.core.common.StateResult
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchIdQualifier
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TwitchAuthenticator @Inject constructor(
    private val refreshTokenRepository: TokenRefresher,
    @TwitchIdQualifier
    private val clientId: String,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            val result = when (val tokens = runBlocking {
                refreshTokenRepository.refreshTokens()
            }) {
                is StateResult.Error, StateResult.Loading -> {
                    null
                }

                is StateResult.Success -> {
                    response.request
                        .newBuilder()
                        .headers(
                            Headers.headersOf(
                                "Authorization", "Bearer ${tokens.data.accessToken}",
                                "Client-ID", clientId
                            )
                        )
                        .build()
                }
            }
            return result
        }
    }
}