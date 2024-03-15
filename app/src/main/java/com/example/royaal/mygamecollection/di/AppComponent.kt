package com.example.royaal.mygamecollection.di

import android.app.Application
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import com.example.royaal.UserSettings
import com.example.royaal.api.ExploreRepositoryProvider
import com.example.royaal.api.GameDetailsRepositoryProvider
import com.example.royaal.api.GamesRepositoryProvider
import com.example.royaal.api.LocalGamesRepositoryProvider
import com.example.royaal.common_android.NetworkMonitorProvider
import com.example.royaal.commonui.Destinations
import com.example.royaal.core.database.di.DatabaseModule
import com.example.royaal.core.database.di.DatabaseProvider
import com.example.royaal.core.network.di.NetworkProvider
import com.example.royaal.core.network.di.RawgNetworkModule
import com.example.royaal.core.network.twitchgamedatabse.di.TwitchModule
import com.example.royaal.data.DataModule
import com.example.royaal.data.DataProvider
import com.example.royaal.data.di.ExploreRepositoryModule
import com.example.royaal.data.di.GameDetailsRepositoryModule
import com.example.royaal.data.di.LocalGamesRepositoryModule
import com.example.royaal.feature_home.di.GamesRepoModule
import com.example.royaal.feature_settings.SettingsViewModel
import com.example.royaal.mygamecollection.MainActivityViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton Component(
    modules = [
        SystemModule::class,
        DatabaseModule::class,
        RawgNetworkModule::class,
        DataModule::class,
        GamesRepoModule::class,
        GameDetailsRepositoryModule::class,
        LocalGamesRepositoryModule::class,
        ExploreRepositoryModule::class,
        NavigationModule::class,
        TwitchModule::class
    ]
)]
interface ApplicationComponent :
    DataProvider,
    DatabaseProvider,
    NetworkProvider,
    NetworkMonitorProvider,
    GameDetailsRepositoryProvider,
    GamesRepositoryProvider,
    LocalGamesRepositoryProvider,
    ExploreRepositoryProvider {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application,
            @BindsInstance
            dataStore: DataStore<UserSettings>,
            @BindsInstance
            tokensSharedPrefs: SharedPreferences
        ): ApplicationComponent
    }

    val viewModelFactory: MainActivityViewModel.Factory

    val settingsViewModelFactory: SettingsViewModel.Factory

    val destinations: Destinations
}