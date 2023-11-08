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

    sealed class ExploreScreenEvent {
        data object LoadUpcomingReleases : ExploreScreenEvent()
        data object LoadLatestReleases : ExploreScreenEvent()
        data class SearchForGames(val query: String) : ExploreScreenEvent()
    }

    fun sendEvent(e: ExploreScreenEvent) {
        viewModelScope.launch {
            when(e) {
                ExploreScreenEvent.LoadLatestReleases -> loadLatestReleases()
                ExploreScreenEvent.LoadUpcomingReleases -> loadUpcomingReleases()
                is ExploreScreenEvent.SearchForGames -> searchForGames(e.query)
            }
        }
    }

    private suspend fun loadLatestReleases() {

    }

    private suspend fun loadUpcomingReleases() {

    }

    private suspend fun searchForGames(query: String) {

    }

}