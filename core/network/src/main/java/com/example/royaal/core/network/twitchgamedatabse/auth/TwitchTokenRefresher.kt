package com.example.royaal.core.network.twitchgamedatabse.auth

import com.example.royaal.core.common.StateResult
import com.example.royaal.core.common.model.UserTokens
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchIdQualifier
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchSecretQualifier
import com.example.royaal.sharedprefs.TokensStorage
import javax.inject.Inject

class TwitchTokenRefresher @Inject constructor(
    private val twitchAuth: TwitchAuth,
    @TwitchIdQualifier
    private val clientId: String,
    @TwitchSecretQualifier
    private val secret: String,
    private val tokensStorage: TokensStorage
) : TokenRefresher {

    override suspend fun refreshTokens(): StateResult<UserTokens> {
        val token = tokensStorage.userToken ?: ""
        val validations = twitchAuth.validateTokens("Bearer $token")
        return if (validations.isSuccessful) {
            StateResult.Success(UserTokens(token, "Bearer"))
        } else {
            val refresh = twitchAuth.refreshTokens(clientId, secret)
            if (refresh.isSuccessful) {
                StateResult.Success(UserTokens(refresh.body()!!.accessToken, "Bearer"))
            } else {
                StateResult.Error(Throwable("Unable to authorize user"))
            }
        }
    }
}