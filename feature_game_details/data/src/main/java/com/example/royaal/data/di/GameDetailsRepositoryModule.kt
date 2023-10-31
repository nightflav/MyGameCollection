package com.example.royaal.data.di

import com.example.royaal.data.repository.GameDetailRepositoryImpl
import com.example.royaal.domain.GameDetailsRepository
import dagger.Binds
import dagger.Module

@Module
interface GameDetailsRepositoryModule {

    @Binds
    fun bindsGamesRepo(repo: GameDetailRepositoryImpl): GameDetailsRepository
}