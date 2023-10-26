package com.example.royaal.data

import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindUserSettingsRepo(userSettingsRepositoryImpl: UserSettingsRepositoryImpl): UserSettingsRepository

}