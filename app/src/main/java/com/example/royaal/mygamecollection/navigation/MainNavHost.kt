package com.example.royaal.mygamecollection.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.royaal.api.GameDetailsEntry
import com.example.royaal.api.HomeEntry
import com.example.royaal.commonui.Destinations
import com.example.royaal.commonui.find
import com.example.royaal.presentation.GameDetailsEntryImpl
import com.example.royaal.presentation.HomeEntryImpl

private val destinations: Destinations = mapOf(
    HomeEntry::class.java to HomeEntryImpl(),
    GameDetailsEntry::class.java to GameDetailsEntryImpl(),
)

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(),
) {
        NavHost(
            navController = navController,
            startDestination = destinations.find<HomeEntry>().destination(),
            modifier = modifier.padding(paddingValues)
        ) {
            with(destinations.find<HomeEntry>()) {
                screen(navController, destinations)
            }
            with(destinations.find<GameDetailsEntry>()) {
                screen(navController, destinations)
            }
        }
}
