package com.example.royaal.domain.usecases

import com.example.royaal.core.common.asResult
import com.example.royaal.domain.PlatformDetailsRepository
import javax.inject.Inject

class LoadPlatformDetailsUseCase @Inject constructor(
    private val repo: PlatformDetailsRepository
) {

    operator fun invoke(id: Int) = repo.getPlatformDetails(id).asResult()

}