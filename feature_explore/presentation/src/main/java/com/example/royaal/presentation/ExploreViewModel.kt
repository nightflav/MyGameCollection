package com.example.royaal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royaal.core.common.fetchResult
import com.example.royaal.core.common.model.uimodel.UiModel
import com.example.royaal.domain.usecases.LoadAllPlatformsUseCase
import com.example.royaal.domain.usecases.LoadByPlatformUseCase
import com.example.royaal.domain.usecases.LoadLatestReleasesUseCase
import com.example.royaal.domain.usecases.LoadUpcomingGamesUseCase
import com.example.royaal.domain.usecases.SearchQueryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor(
    private val searchQueryUseCase: SearchQueryUseCase,
    private val loadLatestReleasesUseCase: LoadLatestReleasesUseCase,
    private val loadUpcomingGamesUseCase: LoadUpcomingGamesUseCase,
    private val loadAllPlatformsUseCase: LoadAllPlatformsUseCase,
    private val loadByPlatformUseCase: LoadByPlatformUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreScreenState())
    val state = _state.asStateFlow()
    private val currState
        get() = state.value

    init {
        viewModelScope.launch {
            loadAllPlatformsUseCase().collect {
                it.fetchResult(
                    onLoading = {},
                    onError = {},
                    onSuccess = { platforms ->
                        _state.emit(
                            currState.copy(
                                exploreGamesState = currState.exploreGamesState.copy(
                                    platforms = platforms,
                                ),
                                isLoading = false
                            )
                        )
                    }
                )
            }
        }
        sendEvent(ExploreScreenEvent.LoadUpcomingReleases)
        sendEvent(ExploreScreenEvent.LoadLatestReleases)
    }

    sealed interface ExploreScreenEvent {
        data object LoadUpcomingReleases : ExploreScreenEvent
        data object LoadLatestReleases : ExploreScreenEvent
        data object SearchForGames : ExploreScreenEvent
        data class SelectPlatform(val newPlatform: Int) : ExploreScreenEvent
        data class QueryChange(val newQuery: String) : ExploreScreenEvent

        data class SelectDeveloper(val developer: Int) : ExploreScreenEvent
    }

    fun sendEvent(e: ExploreScreenEvent) {
        viewModelScope.launch {
            when (e) {
                ExploreScreenEvent.LoadLatestReleases -> loadLatestReleases()
                ExploreScreenEvent.LoadUpcomingReleases -> loadUpcomingReleases()
                ExploreScreenEvent.SearchForGames -> searchForGames()
                is ExploreScreenEvent.SelectPlatform -> selectPlatform(e.newPlatform)
                is ExploreScreenEvent.QueryChange -> changeQuery(e.newQuery)
                is ExploreScreenEvent.SelectDeveloper -> selectDeveloper(e.developer)
            }
        }
    }

    private suspend fun selectDeveloper(devId: Int) {
        _state.emit(
            currState.copy(
                exploreGamesState = currState.exploreGamesState.copy(
                    selectedDeveloper = currState.exploreGamesState.devs
                        .first { it.id == devId }.name
                )
            )
        )
    }

    private suspend fun selectPlatform(platformId: Int) {
        _state.emit(
            currState.copy(
                exploreGamesState = currState.exploreGamesState.copy(
                    selectedPlatform = currState.exploreGamesState.platforms
                        .first { it.id == platformId }.name
                )
            )
        )
        loadByPlatformUseCase(platformId).collect {
            it.fetchResult(
                onLoading = {
                    _state.emit(
                        currState.copy(
                            exploreGamesState = currState.exploreGamesState.copy(
                                areExploreLoading = true
                            )
                        )
                    )
                },
                onError = ::onError,
                onSuccess = { games ->
                    _state.emit(
                        currState.copy(
                            exploreGamesState = currState.exploreGamesState.copy(
                                areExploreLoading = false,
                                explore = games
                            )
                        )
                    )
                }
            )
        }
    }

    private suspend fun loadLatestReleases() {
        loadLatestReleasesUseCase().collect {
            it.fetchResult(
                onLoading = ::onLoading,
                onError = ::onError,
                onSuccess = { games ->
                    _state.emit(
                        currState.copy(
                            latestGamesState = currState.latestGamesState.copy(
                                latest = games,
                                areLatestLoading = false
                            ),
                            error = null
                        )
                    )
                }
            )
        }
    }

    private suspend fun loadUpcomingReleases() {
        loadUpcomingGamesUseCase().collect {
            it.fetchResult(
                onLoading = ::onLoading,
                onError = ::onError,
                onSuccess = { games ->
                    _state.emit(
                        currState.copy(
                            upcomingGamesState = currState.upcomingGamesState.copy(
                                upcoming = games,
                                areUpcomingLoading = false
                            ),
                            error = null
                        )
                    )
                }
            )
        }
    }

    private suspend fun searchForGames() {
        if (currState.searchState.searchQuery.isNotEmpty()) {
            _state.emit(
                currState.copy(
                    searchState = currState.searchState.copy(
                        isLoading = true
                    )
                )
            )
            val result = mutableListOf<UiModel>()
            searchQueryUseCase(currState.searchState.searchQuery).onEach {
                it.fetchResult(
                    onLoading = {},
                    onError = {},
                    onSuccess = { results ->
                        result.addAll(results)
                    }
                )
            }.onCompletion {
                _state.emit(
                    currState.copy(
                        searchState = currState.searchState.copy(
                            searchResult = result,
                            previousQueries =
                            (currState.searchState.previousQueries + currState.searchState.searchQuery)
                                .takeLast(10),
                            isLoading = false
                        )
                    )
                )
            }.collect()
        }
    }

    private suspend fun changeQuery(newQuery: String) {
        _state.emit(
            currState.copy(
                searchState = currState.searchState.copy(
                    searchQuery = newQuery,
                )
            )
        )
    }

    private suspend fun onLoading() = _state.emit(
        currState.copy(
            isLoading = true,
            error = null
        )
    )

    private suspend fun onError(error: Throwable?) = _state.emit(
        currState.copy(
            isLoading = false,
            error = error,
            exploreGamesState = currState.exploreGamesState.copy(
                areExploreLoading = false
            ),
            searchState = currState.searchState.copy(
                isLoading = false
            ),
            upcomingGamesState = currState.upcomingGamesState.copy(
                areUpcomingLoading = false
            ),
            latestGamesState = currState.latestGamesState.copy(
                areLatestLoading = false
            )
        )
    )

}