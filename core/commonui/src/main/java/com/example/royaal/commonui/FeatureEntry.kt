package com.example.royaal.commonui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

typealias Destinations = Map<Class<out FeatureEntry>, FeatureEntry>

inline fun <reified T : FeatureEntry> Destinations.find(): T =
    this[T::class.java] as T

interface FeatureEntry {

    val selectedIcon: ImageVector?
    val unselectedIcon: ImageVector?
    val name: String
    val featureRoute: String

    val args: List<NamedNavArgument>

    @Composable
    fun Screen(
        navController: NavHostController,
        destinations: Destinations,
        backStackEntry: NavBackStackEntry
    )

    fun NavGraphBuilder.screen(
        navController: NavHostController,
        destinations: Destinations
    ) {
        composable(
            route = featureRoute,
            arguments = args
        ) { backStackEntry ->
            Screen(navController, destinations, backStackEntry)
        }
    }
}