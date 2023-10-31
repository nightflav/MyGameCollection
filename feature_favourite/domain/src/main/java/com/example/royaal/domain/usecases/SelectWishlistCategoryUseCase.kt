package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.LocalGamesRepository
import javax.inject.Inject

class SelectWishlistCategoryUseCase @Inject constructor(
    private val localGamesRepository: LocalGamesRepository
) {

    operator fun invoke() = localGamesRepository.getWishListGames().asResult()

}