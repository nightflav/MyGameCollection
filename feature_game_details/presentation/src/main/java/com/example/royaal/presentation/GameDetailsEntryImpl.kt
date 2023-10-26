package com.example.royaal.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.LocalGameDetailsProvider
import com.example.royaal.commonui.DaggerViewModelProvider
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.find
import com.example.royaal.core.database.di.LocalDatabaseProvider
import com.example.royaal.core.network.di.LocalNetworkProvider
import com.example.royaal.presentation.di.DaggerDetailsComponent
import com.example.royaal.presentation.ui.DetailsRoute

class GameDetailsEntryImpl : GameDetailsEntry() {

    @Composable
    override fun Screen(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        val databaseProvider = LocalDatabaseProvider.current
        val gameDetailRepoProvider = LocalGameDetailsProvider.current
        val networkProvider = LocalNetworkProvider.current
        val gameDetailsComponent = DaggerDetailsComponent.factory().create(
            databaseProvider = databaseProvider,
            networkProvider = networkProvider,
            gameDetailRepoProvider = gameDetailRepoProvider,
        )
        val viewModel = DaggerViewModelProvider.daggerViewModel {
            gameDetailsComponent.detailsViewModel
        }
        viewModel.eventQueue.trySend(
            DetailsViewModel.DetailsScreenEvents.LoadScreen(
                backStackEntry.arguments?.getInt("id")!!
            )
        )
        Log.d("TAGTAG", "RECOMPOSE and id = ${backStackEntry.arguments?.getInt("id")}")
        DetailsRoute(
            viewModel = viewModel,
            onBackPressed = {
                navController.popBackStack()
            },
            onSimilarGameClick = {
                val destination = destinations.find<GameDetailsEntry>()
                    .destination(it)
                navController.navigate(destination)
            },
            onPlatformClick = {

            },
        )
    }

}