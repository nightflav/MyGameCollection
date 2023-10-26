package com.example.royaal.data

import androidx.compose.runtime.compositionLocalOf

interface DataProvider {

    val userSettingsRepo: UserSettingsRepository

}

val LocalDataProvider = compositionLocalOf<DataProvider> {
    error("No data provided to DataModule")
}