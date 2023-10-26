package com.example.royaal.domain.usecases

import com.example.royaal.domain.GameDetailsRepository
import javax.inject.Inject

class RemoveGameFromCompletedUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository
) {

    suspend operator fun invoke(id: Int) =
        gameDetailsRepository.removeGameFromCompleted(id)

}