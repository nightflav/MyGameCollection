package com.example.royaal.feature_home.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.feature_home.repository.GamesRepository
import javax.inject.Inject

class GetLatestReleasesUseCase @Inject constructor(
    private val gameRepo: GamesRepository
) {
    operator fun invoke(shouldFetch: Boolean = false) =
        gameRepo.getLatestReleases(shouldFetch).asResult()

}