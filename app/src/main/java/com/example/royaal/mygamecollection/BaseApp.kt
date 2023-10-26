package com.example.royaal.mygamecollection

import android.app.Application
import android.content.Context
import androidx.datastore.dataStore
import com.example.royaal.datastore.UserSettingsSerializer
import com.example.royaal.mygamecollection.di.ApplicationComponent
import com.example.royaal.mygamecollection.di.DaggerApplicationComponent

class BaseApp : Application() {

    private val Context.myDataStore by dataStore(
        fileName = "my_game_collection",
        serializer = UserSettingsSerializer
    )

    val appComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            myDataStore
        )
    }
}