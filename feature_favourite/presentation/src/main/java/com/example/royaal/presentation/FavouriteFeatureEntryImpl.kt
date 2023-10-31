package com.example.royaal.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.royaal.api.FavouriteFeatureEntry
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.LocalLocalGamesRepositoryProvider
import com.example.royaal.commonui.DaggerViewModelProvider
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.find
import com.example.royaal.core.database.di.LocalDatabaseProvider
import com.example.royaal.presentation.di.DaggerFavouriteComponent
import com.example.royaal.presentation.ui.FavouriteRoute
import javax.inject.Inject

class FavouriteFeatureEntryImpl @Inject constructor() : FavouriteFeatureEntry() {
    override val selectedIcon: ImageVector
        get() = Icons.Filled.Favorite
    override val unselectedIcon: ImageVector
        get() = Icons.Outlined.FavoriteBorder
    override val name: String
        get() = "Favourite"

    @Composable
    override fun Screen(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        val localGamesRepositoryProvider = LocalLocalGamesRepositoryProvider.current
        val databaseProvider = LocalDatabaseProvider.current
        val favComponent = DaggerFavouriteComponent.factory().create(
            databaseProvider = databaseProvider,
            localGamesRepositoryProvider = localGamesRepositoryProvider
        )
        val viewModel = DaggerViewModelProvider.daggerViewModel {
            favComponent.favouriteViewModel
        }
        FavouriteRoute(
            viewModel = viewModel,
            onGameClick = {
                val destination = destinations
                    .find<GameDetailsEntry>()
                    .destination(it)
                navController.navigate(destination)
            }
        )
    }
}