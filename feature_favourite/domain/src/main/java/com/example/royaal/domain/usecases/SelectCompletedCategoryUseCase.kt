package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.LocalGamesRepository
import javax.inject.Inject

class  SelectCompletedCategoryUseCase @Inject constructor(
    private val localGamesRepository: LocalGamesRepository
) {

    operator fun invoke() = localGamesRepository.getCompletedGames().asResult()

}