package com.example.royaal.sharedprefs

import android.content.SharedPreferences
import javax.inject.Inject

class TokensStorage @Inject constructor(
    private val tokensPreferences: SharedPreferences
) {

    val userToken = tokensPreferences.getString(ACCESS_TOKEN_KEY, null)

    fun saveAccessToken(newToken: String?) {
        tokensPreferences.edit()
            .putString(ACCESS_TOKEN_KEY, newToken)
            .apply()
    }

    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token_key"
    }
}