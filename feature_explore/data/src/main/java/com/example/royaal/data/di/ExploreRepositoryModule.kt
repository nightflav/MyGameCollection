package com.example.royaal.data.di

import com.example.royaal.data.repository.ExploreRepositoryImpl
import com.example.royaal.domain.ExploreRepository
import dagger.Binds
import dagger.Module

@Module
interface ExploreRepositoryModule {

    @Binds
    fun bindExploreRepository(repo: ExploreRepositoryImpl): ExploreRepository
}