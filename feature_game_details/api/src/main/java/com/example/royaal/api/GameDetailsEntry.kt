package com.example.royaal.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.royaal.commonui.FeatureEntry

abstract class GameDetailsEntry : FeatureEntry {

    companion object {
        private const val GAME_DETAILS_ROUTE = "details_screen"
    }

    final override val featureRoute: String
        get() = "$GAME_DETAILS_ROUTE/{id}"

    final override val args: List<NamedNavArgument>
        get() = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )

    fun destination(id: Int) = "$GAME_DETAILS_ROUTE/$id"

}