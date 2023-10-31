package com.example.royaal.api

import androidx.navigation.NamedNavArgument
import com.example.royaal.commonui.FeatureEntry

abstract class FavouriteFeatureEntry : FeatureEntry {

    companion object {
        private const val FAVOURITE_ROUTE = "favourite_route"
    }

    final override val featureRoute: String
        get() = FAVOURITE_ROUTE

    final override val args: List<NamedNavArgument>
        get() = emptyList()

    fun destination(id: Int) = featureRoute
}