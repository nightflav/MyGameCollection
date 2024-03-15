package com.example.royaal.presentation

import com.example.royaal.core.common.model.uimodel.Developer
import com.example.royaal.core.common.model.uimodel.Platform
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.UiModel

data class ExploreScreenState(
    val searchState: SearchState = SearchState(),
    val upcomingGamesState: UpcomingGamesState = UpcomingGamesState(),
    val latestGamesState: LatestGamesState = LatestGamesState(),
    val exploreGamesState: ExploreGamesState = ExploreGamesState(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)

data class SearchState(
    val searchResult: List<UiModel> = emptyList(),
    val searchQuery: String = "",
    val previousQueries: List<String> = emptyList(),
    val isLoading: Boolean = true,
)

data class UpcomingGamesState(
    val upcoming: List<PreviewGameModel> = emptyList(),
    val areUpcomingLoading: Boolean = true,
)

data class LatestGamesState(
    val latest: List<PreviewGameModel> = emptyList(),
    val areLatestLoading: Boolean = true,
)

data class ExploreGamesState(
    val explore: List<PreviewGameModel> = emptyList(),
    val areExploreLoading: Boolean = true,
    val selectedPlatform: String = "undefined",
    val selectedDeveloper: String = "undefined",
    val platforms: List<Platform> = emptyList(),
    val devs: List<Developer> = emptyList()
)