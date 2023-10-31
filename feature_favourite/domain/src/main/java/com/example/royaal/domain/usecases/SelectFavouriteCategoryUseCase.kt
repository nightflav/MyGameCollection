package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.LocalGamesRepository
import javax.inject.Inject

class SelectFavouriteCategoryUseCase @Inject constructor(
    private val localGamesRepository: LocalGamesRepository
) {

    operator fun invoke() = localGamesRepository.getFavouriteGames().asResult()

}