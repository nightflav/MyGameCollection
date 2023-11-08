package com.example.royaal.api

import androidx.navigation.NamedNavArgument
import com.example.royaal.commonui.FeatureEntry

abstract class ExploreFeatureEntry : FeatureEntry {

    companion object {
        private const val EXPLORE_ROUTE = "details_screen"
    }

    final override val featureRoute: String
        get() = EXPLORE_ROUTE

    final override val args: List<NamedNavArgument>
        get() = emptyList()

    fun destination() = featureRoute

}