package com.example.royaal.domain

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.core.common.model.uimodel.PlatformDetailsModel
import kotlinx.coroutines.flow.Flow

interface PlatformDetailsRepository {

    fun getPlatformDetails(id: Int): Flow<PlatformDetailsModel>

    fun getPlatformGames(id: Int): Flow<DetailsGameModel>

    suspend fun setPlatformAsFavourite(id: Int)
}