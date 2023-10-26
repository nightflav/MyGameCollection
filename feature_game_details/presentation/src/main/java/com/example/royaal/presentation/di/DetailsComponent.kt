package com.example.royaal.presentation.di

import com.example.royaal.api.GameDetailsRepoProvider
import com.example.royaal.core.common.di.PerFeature
import com.example.royaal.core.database.di.DatabaseProvider
import com.example.royaal.core.network.di.NetworkProvider
import com.example.royaal.presentation.DetailsViewModel
import dagger.Component

@PerFeature
@Component(
    dependencies = [
        DatabaseProvider::class,
        NetworkProvider::class,
        GameDetailsRepoProvider::class
    ]
)
internal interface DetailsComponent {

    @PerFeature
    val detailsViewModel: DetailsViewModel

    @Component.Factory
    interface Factory {
        fun create(
            databaseProvider: DatabaseProvider,
            networkProvider: NetworkProvider,
            gameDetailRepoProvider: GameDetailsRepoProvider
        ): DetailsComponent
    }

}