package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.ExploreRepository
import javax.inject.Inject

class LoadByPlatformUseCase @Inject constructor(
    private val repo: ExploreRepository
) {

    operator fun invoke(platform: Int) = repo.getByPlatform(platform).asResult()

}