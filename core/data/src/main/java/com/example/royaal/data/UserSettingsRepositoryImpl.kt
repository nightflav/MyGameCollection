package com.example.royaal.data

import com.example.royaal.core.common.model.DarkThemeConfiguration
import com.example.royaal.core.common.model.ThemeBrandConf
import com.example.royaal.core.common.model.UserSettings
import com.example.royaal.datastore.UserDataStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingsRepositoryImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : UserSettingsRepository {
    override fun getUserSettings(): Flow<UserSettings> =
        userDataStore.userSettings

    override suspend fun setDarkThemeConfig(newConfiguration: DarkThemeConfiguration) {
        userDataStore.setDarkThemeConfig(newConfiguration)
    }

    override suspend fun setUseDynamicColor(shouldUse: Boolean) {
        userDataStore.setUseDynamicColor(shouldUse)
    }

    override suspend fun setThemeBrand(newThemeBrand: ThemeBrandConf) {
        userDataStore.setThemeBrand(newThemeBrand)
    }
}