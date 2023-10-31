package com.example.royaal.presentation

import com.example.royaal.core.common.model.uimodel.DetailsGameModel

internal data class FavouriteScreenState(
    val selectedCategory: FavouriteScreenCategory = FavouriteScreenCategory.Undefined,
    val games: List<DetailsGameModel> = emptyList(),
    val areGamesLoading: Boolean = true,
    val isLoading: Boolean = true,
    val error: Throwable? = null
)