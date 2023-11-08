package com.example.royaal.presentation

import com.example.royaal.core.common.model.uimodel.PreviewGameModel

data class ExploreScreenState(
    val searchResult: List<PreviewGameModel> = emptyList(),
    val searchQuery: String = "",
    val selectedGenre: String = "undefined",
    val previousQueries: List<String> = emptyList(),
    val upcoming: List<PreviewGameModel> = emptyList(),
    val latest: List<PreviewGameModel> = emptyList(),
    val explore: List<PreviewGameModel> = emptyList(),
    val isLoading: Boolean = true,
    val error: Throwable? = null
)
