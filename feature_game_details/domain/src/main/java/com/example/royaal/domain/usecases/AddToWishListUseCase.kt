package com.example.royaal.domain.usecases

import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.domain.GameDetailsRepository
import javax.inject.Inject

class AddToWishListUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository
) {

    suspend operator fun invoke(game: DetailsGameModel) =
        gameDetailsRepository.addGameToWishlist(game)

}