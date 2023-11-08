package com.example.royaal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExploreViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ExploreScreenState())
    val state = _state.asStateFlow()
    private val currState
        get() = state.value

    sealed interface ExploreScreenEvent {
        data object LoadUpcomingReleases : ExploreScreenEvent
        data object LoadLatestReleases : ExploreScreenEvent
        data object SearchForGames : ExploreScreenEvent
        data class SelectGenre(val newGenre: String) : ExploreScreenEvent
        data class QueryChange(val newQuery: String) : ExploreScreenEvent
    }

    fun sendEvent(e: ExploreScreenEvent) {
        viewModelScope.launch {
            when (e) {
                ExploreScreenEvent.LoadLatestReleases -> loadLatestReleases()
                ExploreScreenEvent.LoadUpcomingReleases -> loadUpcomingReleases()
                ExploreScreenEvent.SearchForGames -> searchForGames()
                is ExploreScreenEvent.SelectGenre -> selectGenre(e.newGenre)
                is ExploreScreenEvent.QueryChange -> changeQuery(e.newQuery)
            }
        }
    }

    private suspend fun selectGenre(genre: String) {

    }

    private suspend fun loadLatestReleases() {

    }

    private suspend fun loadUpcomingReleases() {

    }

    private suspend fun searchForGames() {

    }

    private suspend fun changeQuery(newQuery: String) {
        _state.emit(
            currState.copy(
                searchQuery = newQuery
            )
        )
    }

}