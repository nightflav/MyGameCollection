package com.example.royaal.mygamecollection.di

import android.app.Application
import androidx.datastore.core.DataStore
import com.example.royaal.UserSettings
import com.example.royaal.api.GameDetailsRepoProvider
import com.example.royaal.api.GamesRepositoryProvider
import com.example.royaal.core.database.di.DatabaseModule
import com.example.royaal.core.database.di.DatabaseProvider
import com.example.royaal.core.network.di.NetworkModule
import com.example.royaal.core.network.di.NetworkProvider
import com.example.royaal.data.DataModule
import com.example.royaal.data.DataProvider
import com.example.royaal.data.di.GameDetailRepoModule
import com.example.royaal.feature_home.di.GamesRepoModule
import com.example.royaal.feature_settings.SettingsViewModel
import com.example.royaal.mygamecollection.MainActivityViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(
    modules = [
        DatabaseModule::class,
        NetworkModule::class,
        GamesRepoModule::class,
        DataModule::class,
        GameDetailRepoModule::class
    ]
)]
interface ApplicationComponent :
    DataProvider,
    DatabaseProvider,
    NetworkProvider,
    GameDetailsRepoProvider,
    GamesRepositoryProvider {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application,
            @BindsInstance
            dataStore: DataStore<UserSettings>
        ): ApplicationComponent
    }

    val viewModelFactory: MainActivityViewModel.Factory

    val settingsViewModelFactory: SettingsViewModel.Factory


}