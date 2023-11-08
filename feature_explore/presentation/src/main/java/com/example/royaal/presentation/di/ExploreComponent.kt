package com.example.royaal.presentation.di

import com.example.royaal.api.ExploreRepositoryProvider
import com.example.royaal.core.common.di.PerFeature
import com.example.royaal.core.network.di.NetworkModule
import com.example.royaal.presentation.ExploreViewModel
import dagger.Component

@PerFeature
@Component(
    dependencies = [
        ExploreRepositoryProvider::class,
        NetworkModule::class
    ]
)
interface ExploreComponent {

    @PerFeature
    val exploreViewModel: ExploreViewModel

    @Component.Factory
    interface Factory {
        fun create(
            exploreRepositoryProvider: ExploreRepositoryProvider,
            networkModule: NetworkModule
        ): ExploreComponent
    }
}