package com.example.royaal.feature_home.di

import com.example.royaal.feature_home.GamesRepositoryImpl
import com.example.royaal.feature_home.repository.GamesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface GamesRepoModule {

    @Singleton
    @Binds
    fun bindsGamesRepository(gameRepositoryImpl: GamesRepositoryImpl): GamesRepository

}