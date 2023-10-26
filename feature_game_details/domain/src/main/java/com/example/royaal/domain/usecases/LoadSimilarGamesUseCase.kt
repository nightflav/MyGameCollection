package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.GameDetailsRepository
import javax.inject.Inject

class LoadSimilarGamesUseCase @Inject constructor(
    private val gamesDetailsRepo: GameDetailsRepository
) {

    operator fun invoke(id: Int) = gamesDetailsRepo
        .getGamesFromSameSeries(id)
        .asResult()

}