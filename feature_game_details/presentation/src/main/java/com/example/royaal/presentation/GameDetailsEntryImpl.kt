package com.example.royaal.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.outlined.Details
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
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
import javax.inject.Inject

class GameDetailsEntryImpl @Inject constructor() : GameDetailsEntry() {
    override val selectedIcon: ImageVector
        get() = Icons.Default.Details
    override val unselectedIcon: ImageVector
        get() = Icons.Outlined.Details
    override val name: String
        get() = "details"

    @Composable
    override fun Screen(
        modifier: Modifier,
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
            gameDetailsRepoProvider = gameDetailRepoProvider,
        )
        val viewModel = DaggerViewModelProvider.daggerViewModel {
            gameDetailsComponent.detailsViewModel
        }
        LaunchedEffect(key1 = gameDetailRepoProvider) {
            viewModel.sendEvent(
                DetailsViewModel.DetailsScreenEvents.LoadScreen(
                    backStackEntry.arguments?.getInt("id")!!
                )
            )
            viewModel.sendEvent(
                DetailsViewModel.DetailsScreenEvents.LoadSimilarGames
            )
        }

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