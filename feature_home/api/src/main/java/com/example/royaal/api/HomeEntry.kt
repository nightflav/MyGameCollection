package com.example.royaal.api

import androidx.navigation.NamedNavArgument
import com.example.royaal.commonui.FeatureEntry

abstract class HomeEntry() : FeatureEntry {

    final override val featureRoute: String
        get() = "home_route"

    final override val args: List<NamedNavArgument>
        get() = emptyList()

    fun destination() = featureRoute

}