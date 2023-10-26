package com.example.royaal.data

import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import com.example.royaal.core.common.model.UserSettings
import kotlinx.coroutines.flow.Flow

interface UserSettingsRepository {

    fun getUserSettings(): Flow<UserSettings>

    suspend fun setDarkThemeConfig(newConfiguration: DarkThemeConfiguration)

    suspend fun setUseDynamicColor(shouldUse: Boolean)

    suspend fun setThemeBrand(newThemeBrand: ThemeBrandConf)

}