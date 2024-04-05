package com.example.royaal.core.network.twitchgamedatabse

import android.util.Log
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.TwitchAuthenticator
import com.api.igdb.request.games
import com.example.royaal.core.network.twitchgamedatabse.model.wrapper.TwitchGame
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchIdQualifier
import com.example.royaal.core.network.twitchgamedatabse.qualifiers.TwitchSecretQualifier
import com.example.royaal.sharedprefs.TokensStorage
import java.sql.Timestamp
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class TwitchWrapperApiImpl @Inject constructor(
    @TwitchIdQualifier
    private val clientId: String,
    @TwitchSecretQualifier
    private val clientSecret: String,
    tokensStorage: TokensStorage
) : TwitchWrapperApi {

    init {
        if (tokensStorage.userToken == null) {
            val token = TwitchAuthenticator.requestTwitchToken(clientId, clientSecret)
            tokensStorage.saveAccessToken(token?.access_token)
        }
        IGDBWrapper.setCredentials(clientId, tokensStorage.userToken ?: "")
    }

    override fun getLatestReleases(): List<TwitchGame> {
        Log.d("TAGTAG", "1")
        val now = Timestamp.from(
            Instant.now().minusSeconds(ChronoUnit.MONTHS.duration.seconds)
        ).time
        Log.d("TAGTAG", "$now")
        val games =
            IGDBWrapper.games(
                APICalypse().fields("*")
                .where("first_release_date>$now")
            ).map {
                TwitchGame(name = it.name, id = it.id, cover = it.cover.url)
            }
        Log.d("TAGTAG", "$games")
        return games
    }

    override fun getUpcomingReleases(): List<TwitchGame> {
        val now = Timestamp.from(Instant.now()).time
        val games = IGDBWrapper.games(APICalypse().where("first_release_date>$now")).map {
            TwitchGame(name = it.name, id = it.id, cover = it.cover.url)
        }
        return games
    }

    override fun getMostRatedReleases(): List<TwitchGame> {
        val now = Timestamp.from(
            Instant.now().minusSeconds(ChronoUnit.MONTHS.duration.seconds)
        ).time
        val games = IGDBWrapper.games(
            APICalypse()
                .where("first_release_date>$now & rating_count>5")
                .sort("rating", Sort.DESCENDING)
        ).map {
            TwitchGame(name = it.name, id = it.id, cover = it.cover.url)
        }
        return games
    }
}