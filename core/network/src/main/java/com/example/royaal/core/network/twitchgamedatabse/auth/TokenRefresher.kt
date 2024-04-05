package com.example.royaal.core.network.twitchgamedatabse.auth

import com.example.royaal.core.common.StateResult
import com.example.royaal.core.common.model.UserTokens

interface TokenRefresher {
    suspend fun refreshTokens(): StateResult<UserTokens>
}