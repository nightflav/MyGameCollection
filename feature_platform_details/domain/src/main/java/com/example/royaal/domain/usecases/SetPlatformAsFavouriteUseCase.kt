package com.example.royaal.domain.usecases

import com.example.royaal.domain.PlatformDetailsRepository
import javax.inject.Inject

class SetPlatformAsFavouriteUseCase @Inject constructor(
    private val repo: PlatformDetailsRepository
) {

    suspend operator fun invoke(id: Int) = repo.setPlatformAsFavourite(id)

}