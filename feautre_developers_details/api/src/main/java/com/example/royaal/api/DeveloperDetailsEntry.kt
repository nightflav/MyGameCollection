package com.example.royaal.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.royaal.commonui.FeatureEntry

abstract class DeveloperDetailsEntry : FeatureEntry {

    companion object {
        private const val DEVELOPERS_DETAILS_ROUTE = "developers_details_screen"
    }

    final override val featureRoute: String
        get() = "$DEVELOPERS_DETAILS_ROUTE/{id}"

    final override val args: List<NamedNavArgument>
        get() = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )

    fun destination(id: Int) = "$DEVELOPERS_DETAILS_ROUTE/$id"
}