package com.example.royaal.mygamecollection.di

import com.example.royaal.api.ExploreFeatureEntry
import com.example.royaal.api.FavouriteFeatureEntry
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.HomeEntry
import com.example.royaal.commonui.FeatureEntry
import com.example.royaal.presentation.ExploreFeatureEntryImpl
import com.example.royaal.presentation.FavouriteFeatureEntryImpl
import com.example.royaal.presentation.GameDetailsEntryImpl
import com.example.royaal.presentation.HomeEntryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface NavigationModule {

    @Singleton
    @Binds
    @RouteKey(HomeEntry::class)
    @IntoMap
    fun bindHomeRoute(homeEntryImpl: HomeEntryImpl): FeatureEntry

    @Singleton
    @Binds
    @RouteKey(GameDetailsEntry::class)
    @IntoMap
    fun bindDetailsRoute(gameDetailsEntryImpl: GameDetailsEntryImpl): FeatureEntry

    @Singleton
    @Binds
    @RouteKey(FavouriteFeatureEntry::class)
    @IntoMap
    fun bindFavouriteRoute(favouriteFeatureEntryImpl: FavouriteFeatureEntryImpl): FeatureEntry

    @Singleton
    @Binds
    @RouteKey(ExploreFeatureEntry::class)
    @IntoMap
    fun bindExploreRoute(exploreFeatureEntryImpl: ExploreFeatureEntryImpl): FeatureEntry

}