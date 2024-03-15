package com.example.royaal.mygamecollection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.dataStore
import com.example.royaal.datastore.UserSettingsSerializer
import com.example.royaal.mygamecollection.di.ApplicationComponent
import com.example.royaal.mygamecollection.di.DaggerApplicationComponent

class BaseApp : Application() {

    private val Context.myDataStore by dataStore(
        fileName = "my_game_collection",
        serializer = UserSettingsSerializer
    )

    private val Context.tokensSharedPrefs: SharedPreferences
        get() = getSharedPreferences("TokensSharedPrefs", Context.MODE_PRIVATE)

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            myDataStore,
            tokensSharedPrefs
        )
    }
}