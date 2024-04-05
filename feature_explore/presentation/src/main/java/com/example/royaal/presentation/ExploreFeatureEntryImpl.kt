package com.example.royaal.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.royaal.api.DeveloperDetailsEntry
import com.example.royaal.api.ExploreFeatureEntry
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.LocalExploreRepositoryProvider
import com.example.royaal.api.PlatformDetailsEntry
import com.example.royaal.commonui.DaggerViewModelProvider
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.find
import com.example.royaal.core.network.di.LocalNetworkProvider
import com.example.royaal.presentation.di.DaggerExploreComponent
import com.example.royaal.presentation.ui.ExploreRoute
import javax.inject.Inject

class ExploreFeatureEntryImpl @Inject constructor() : ExploreFeatureEntry() {

    override val selectedIcon: ImageVector
        get() = Icons.Filled.Explore
    override val unselectedIcon: ImageVector
        get() = Icons.Outlined.Explore
    override val name: String
        get() = "Explore"

    @Composable
    override fun Screen(
        modifier: Modifier,
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    ) {
        val localExploreRepositoryProvider = LocalExploreRepositoryProvider.current
        val networkProvider = LocalNetworkProvider.current
        val exploreComponent = DaggerExploreComponent.factory().create(
            localExploreRepositoryProvider,
            networkProvider
        )
        val viewModel = DaggerViewModelProvider.daggerViewModel {
            exploreComponent.exploreViewModel
        }
        ExploreRoute(
            modifier = modifier,
            viewModel = viewModel,
            onGameClick = {
                val destination = destinations
                    .find<GameDetailsEntry>()
                    .destination(it)
                navController.navigate(destination)
            },
            onDeveloperClick = {
                val destination = destinations
                    .find<DeveloperDetailsEntry>()
                    .destination(it)
                navController.navigate(destination)
            },
            onPlatformClick = {
                val destination = destinations
                    .find<PlatformDetailsEntry>()
                    .destination(it)
                navController.navigate(destination)
            }
        )
    }

}