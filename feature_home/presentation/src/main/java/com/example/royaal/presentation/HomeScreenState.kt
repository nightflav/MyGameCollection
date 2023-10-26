package com.example.royaal.presentation

import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.core.common.model.uimodel.ProfileModel

internal data class HomeScreenState(
    val games: List<PreviewGameModel> = emptyList(),
    val category: HomeGamesCategory = HomeGamesCategory.Undefined,
    val selectedGameIndex: Int = 0,
    val isLoading: Boolean = true,
    val profile: ProfileModel = ProfileModel.emptyProfile,
    val areGamesLoading: Boolean = true,
    val error: Throwable? = null,
)