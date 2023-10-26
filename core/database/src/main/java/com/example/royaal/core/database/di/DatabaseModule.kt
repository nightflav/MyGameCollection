package com.example.royaal.core.database.di

import android.app.Application
import androidx.room.Room
import com.example.royaal.core.database.app.GamesDao
import com.example.royaal.core.database.app.GamesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideGamesDatabase(
        application: Application
    ): GamesDatabase = Room.databaseBuilder(
        application,
        GamesDatabase::class.java,
        "games_database"
    ).build()

    @Provides
    fun providesGameDao(
        database: GamesDatabase
    ): GamesDao = database.gamesDao()

}