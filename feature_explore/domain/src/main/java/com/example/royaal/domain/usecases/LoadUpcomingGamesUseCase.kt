package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.ExploreRepository
import javax.inject.Inject

class LoadUpcomingGamesUseCase @Inject constructor(
    private val repo: ExploreRepository
) {

    operator fun invoke() = repo.getUpcomingGames().asResult()

}