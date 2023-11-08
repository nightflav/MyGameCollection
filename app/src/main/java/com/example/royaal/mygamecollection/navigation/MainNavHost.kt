package com.example.royaal.mygamecollection.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.royaal.api.ExploreFeatureEntry
import com.example.royaal.api.FavouriteFeatureEntry
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.HomeEntry
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.find
import com.example.royaal.commonui.navigation.bottomappbar.BottomGamesBar
import com.example.royaal.feature_settings.SettingsDialog
import com.example.royaal.feature_settings.SettingsViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    settingsViewModel: SettingsViewModel,
    destinations: Destinations
) {
    val navDestinationsFilter = listOf(
        HomeEntry::class.java,
        FavouriteFeatureEntry::class.java,
        ExploreFeatureEntry::class.java
    )
    val navDestinations = destinations.filterKeys { it in navDestinationsFilter }
    Scaffold(
        bottomBar = {
            BottomGamesBar(
                navController = navController,
                destinations = navDestinations
            )
        }
    ) { it ->
        Box(modifier = Modifier.padding()) {
            NavHost(
                navController = navController,
                startDestination = destinations.find<HomeEntry>().destination(),
                exitTransition = {
                    when (initialState.destination.route) {
                        destinations.find<HomeEntry>().featureRoute -> {
                            slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(700)
                            )
                        }

                        destinations.find<FavouriteFeatureEntry>().featureRoute -> {
                            when (targetState.destination.route) {
                                destinations.find<HomeEntry>().featureRoute -> {
                                    slideOutHorizontally(
                                        targetOffsetX = { it },
                                        animationSpec = tween(700)
                                    )
                                }

                                else -> {
                                    fadeOut(tween(700))
                                }
                            }
                        }

                        else -> {
                            fadeOut(tween(700))
                        }
                    }
                },
                enterTransition = {
                    when (targetState.destination.route) {
                        destinations.find<HomeEntry>().featureRoute -> {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(700)
                            )
                        }

                        destinations.find<FavouriteFeatureEntry>().featureRoute -> {
                            when (initialState.destination.route) {
                                destinations.find<HomeEntry>().featureRoute -> {
                                    slideInHorizontally(
                                        initialOffsetX = { it },
                                        animationSpec = tween(700)
                                    )
                                }

                                else -> {
                                    fadeIn(tween(700))
                                }
                            }
                        }

                        else -> {
                            fadeIn(tween(700))
                        }
                    }
                },
            ) {
                with(destinations.find<HomeEntry>()) {
                    screen(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        destinations = destinations
                    )
                }
                with(destinations.find<GameDetailsEntry>()) {
                    screen(
                        navController = navController,
                        destinations = destinations
                    )
                }
                with(destinations.find<FavouriteFeatureEntry>()) {
                    screen(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        destinations = destinations
                    )
                }
                with(destinations.find<ExploreFeatureEntry>()) {
                    screen(
                        modifier = Modifier.padding(it),
                        navController = navController,
                        destinations = destinations
                    )
                }
            }
            Settings(
                navController = navController,
                destinations = destinations,
                settingsViewModel = settingsViewModel
            )
        }
    }
}

@Composable
private fun BoxScope.Settings(
    navController: NavHostController,
    destinations: Destinations,
    settingsViewModel: SettingsViewModel
) {
    var showSettings by remember { mutableStateOf(false) }
    AnimatedVisibility(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        visible = navController.currentBackStackEntryAsState().value?.destination?.route
                == destinations[HomeEntry::class.java]?.featureRoute,
    ) {
        IconButton(
            onClick = { showSettings = true },
            modifier = Modifier
                .padding(DimConst.doublePadding)
                .scale(1.5f)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = null,
            )
        }

    }
    if (showSettings) {
        SettingsDialog(viewModel = settingsViewModel) {
            showSettings = !showSettings
        }
    }
}
