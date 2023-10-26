package com.example.royaal.core.common.navigation

sealed class TopLevelDestinations(val route: String) {
    data object Home : TopLevelDestinations("home_dest")
    data object Fav : TopLevelDestinations("favourite_dest")
    data object Exp : TopLevelDestinations("explore_dest")
}
