package com.example.royaal.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.HomeEntry
import com.example.royaal.api.LocalGamesRepositoryProvider
import com.example.royaal.commonui.DaggerViewModelProvider
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.find
import com.example.royaal.core.network.di.LocalNetworkProvider
import com.example.royaal.presentation.di.DaggerHomeComponent
import com.example.royaal.presentation.ui.HomeRoute
import javax.inject.Inject

class HomeEntryImpl @Inject constructor() : HomeEntry() {
    override val selectedIcon: ImageVector
        get() = Icons.Filled.Home
    override val unselectedIcon: ImageVector
        get() = Icons.Outlined.Home
    override val name: String
        get() = "Home"


    @Composable
    override fun Screen(
        modifier: Modifier,
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        val networkProvider = LocalNetworkProvider.current
        val gamesRepositoryProvider = LocalGamesRepositoryProvider.current
        val homeComponent = DaggerHomeComponent.factory().create(
            networkProvider = networkProvider,
            gamesRepositoryProvider = gamesRepositoryProvider
        )
        val viewModel = DaggerViewModelProvider.daggerViewModel {
            homeComponent.homeViewModel
        }
        HomeRoute(
            modifier = modifier,
            onGameClick = {
                val destination = destinations
                    .find<GameDetailsEntry>()
                    .destination(it)
                navController.navigate(destination)
            },
            viewModel = viewModel
        )
    }
}