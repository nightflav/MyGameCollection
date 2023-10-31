package com.example.royaal.presentation.di

import com.example.royaal.api.LocalGamesRepositoryProvider
import com.example.royaal.core.common.di.PerFeature
import com.example.royaal.core.database.di.DatabaseProvider
import com.example.royaal.presentation.FavouriteViewModel
import dagger.Component

@PerFeature
@Component(
    dependencies = [
        DatabaseProvider::class,
        LocalGamesRepositoryProvider::class
    ]
)
internal interface FavouriteComponent {

    @PerFeature
    val favouriteViewModel: FavouriteViewModel

    @Component.Factory
    interface Factory {
        fun create(
            databaseProvider: DatabaseProvider,
            localGamesRepositoryProvider: LocalGamesRepositoryProvider
        ): FavouriteComponent
    }

}