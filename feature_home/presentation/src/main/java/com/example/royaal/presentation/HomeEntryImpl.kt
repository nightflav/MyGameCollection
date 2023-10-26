package com.example.royaal.presentation

import android.util.Log
import androidx.compose.runtime.Composable
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

class HomeEntryImpl : HomeEntry() {

    @Composable
    override fun Screen(
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
            onGameClick = {
                val destination = destinations
                    .find<GameDetailsEntry>()
                    .destination(it)
                Log.d("TAGTAG", destination)
                navController.navigate(destination)
            },
            viewModel = viewModel
        )
    }
}