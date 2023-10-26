package com.example.royaal.mygamecollection

import com.example.royaal.core.common.model.UserSettings

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userSettings: UserSettings) : MainActivityUiState
}