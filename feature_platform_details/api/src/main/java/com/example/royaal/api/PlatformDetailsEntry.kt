package com.example.royaal.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.royaal.commonui.FeatureEntry

abstract class PlatformDetailsEntry : FeatureEntry {

    companion object {
        private const val PLATFORM_DETAILS_ROUTE = "platform_details_screen"
    }

    final override val featureRoute: String
        get() = "$PLATFORM_DETAILS_ROUTE/{id}"

    final override val args: List<NamedNavArgument>
        get() = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )

    fun destination(id: Int) = "$PLATFORM_DETAILS_ROUTE/$id"

}