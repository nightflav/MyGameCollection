package com.example.royaal.presentation.di

import com.example.royaal.api.GamesRepositoryProvider
import com.example.royaal.core.common.di.PerFeature
import com.example.royaal.core.network.di.NetworkProvider
import com.example.royaal.presentation.HomeViewModel
import dagger.Component

@PerFeature
@Component(
    dependencies = [
        NetworkProvider::class,
        GamesRepositoryProvider::class
    ]
)
internal interface HomeComponent {

    @PerFeature
    val homeViewModel: HomeViewModel

    @Component.Factory
    interface Factory {
        fun create(
            networkProvider: NetworkProvider,
            gamesRepositoryProvider: GamesRepositoryProvider
        ): HomeComponent
    }

}