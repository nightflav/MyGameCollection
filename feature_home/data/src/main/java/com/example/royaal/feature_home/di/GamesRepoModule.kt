package com.example.royaal.feature_home.di

import com.example.royaal.feature_home.GamesRepositoryImpl
import com.example.royaal.feature_home.repository.GamesRepository
import dagger.Binds
import dagger.Module

@Module
interface GamesRepoModule {

    @Binds
    fun bindsGamesRepository(gameRepositoryImpl: GamesRepositoryImpl): GamesRepository

}