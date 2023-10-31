package com.example.royaal.mygamecollection.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.royaal.api.FavouriteFeatureEntry
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.HomeEntry
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.DimConst
import com.example.royaal.commonui.find
import com.example.royaal.feature_settings.SettingsDialog
import com.example.royaal.feature_settings.SettingsViewModel

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(),
    settingsViewModel: SettingsViewModel,
    destinations: Destinations
) {
    Box {
        NavHost(
            navController = navController,
            startDestination = destinations.find<HomeEntry>().destination(),
            modifier = modifier.padding(bottom = paddingValues.calculateBottomPadding())
        ) {
            with(destinations.find<HomeEntry>()) {
                screen(navController, destinations)
            }
            with(destinations.find<GameDetailsEntry>()) {
                screen(navController, destinations)
            }
            with(destinations.find<FavouriteFeatureEntry>()) {
                screen(navController, destinations)
            }
        }
        Settings(
            navController = navController,
            destinations = destinations,
            settingsViewModel = settingsViewModel
        )
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
        modifier = Modifier.align(Alignment.TopEnd),
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
