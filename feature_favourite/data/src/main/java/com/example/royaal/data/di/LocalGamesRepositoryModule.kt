package com.example.royaal.data.di

import com.example.royaal.data.repository.LocalGamesRepositoryImpl
import com.example.royaal.domain.LocalGamesRepository
import dagger.Binds
import dagger.Module

@Module
interface LocalGamesRepositoryModule {

    @Binds
    fun bindLocalGamesRepo(impl: LocalGamesRepositoryImpl): LocalGamesRepository
}