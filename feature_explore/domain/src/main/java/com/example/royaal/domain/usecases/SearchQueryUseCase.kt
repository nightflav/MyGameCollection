package com.example.royaal.domain.usecases

import com.example.royaal.core.common.StateResult
import com.example.royaal.core.common.asResult
import com.example.royaal.core.common.isSearchRequestForAny
import com.example.royaal.core.common.model.uimodel.UiModel
import com.example.royaal.domain.ExploreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class SearchQueryUseCase @Inject constructor(
    private val repo: ExploreRepository
) {

    operator fun invoke(query: String): Flow<StateResult<List<UiModel>>> {
        val filterQuery = if (query.isSearchRequestForAny(
                "sex", "porn", "drugs", "addiction", "horny", "alcohol",
                "anal", "oral", "cumshot", "gay", "trans", "lesbian",
                "gachi", "fuck", "shit"
            )
        ) "Minecraft" else query
        return merge(
            repo.searchForGames(filterQuery),
            repo.searchForPlatform(filterQuery),
            repo.searchForDevelopers(filterQuery)
        ).asResult()
    }

}