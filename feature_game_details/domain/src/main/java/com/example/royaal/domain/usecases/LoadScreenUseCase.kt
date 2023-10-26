package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.GameDetailsRepository
import javax.inject.Inject


class LoadScreenUseCase @Inject constructor(
    private val gameDetailsRepository: GameDetailsRepository
) {
    operator fun invoke(id: Int) = gameDetailsRepository
        .getGameById(id)
        .asResult()

}