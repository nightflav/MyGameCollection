package com.example.royaal.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royaal.core.common.StateResult
import com.example.royaal.core.common.model.uimodel.PreviewGameModel
import com.example.royaal.feature_home.usecases.GetLatestReleasesUseCase
import com.example.royaal.feature_home.usecases.GetMostRatedLastMonthUseCase
import com.example.royaal.feature_home.usecases.GetUpcomingReleasesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class HomeViewModel @Inject constructor(
    private val getLatestReleasesUseCase: GetLatestReleasesUseCase,
    private val getMostRatedLastMonthUseCase: GetMostRatedLastMonthUseCase,
    private val getUpcomingReleasesUseCase: GetUpcomingReleasesUseCase,
    //private val loadProfileUseCase: LoadProfileUseCase
) : ViewModel() {

    private val _screenState = MutableStateFlow(HomeScreenState(isLoading = true))
    val screenState = _screenState.asStateFlow()
    private val currState get() = screenState.value

    val eventQueue = Channel<HomeScreenEvents>()

    sealed class HomeScreenEvents {
        data class SelectCategory(val id: HomeGamesCategory) : HomeScreenEvents()
        data object InitScreen : HomeScreenEvents()
    }

    init {
        Log.d("tagtag", "init vm home")
        subscribeToEvents()
        viewModelScope.launch {
            eventQueue.send(
                HomeScreenEvents.SelectCategory(
                    HomeGamesCategory.LatestReleases
                )
            )
        }
    }

    private suspend fun loadProfile() {
//        val user = loadProfileUseCase()
//        _screenState.emit(
//            currState.copy(
//                profile = user.toProfileModel()
//            )
//        )
    }

    @OptIn(FlowPreview::class)
    private fun subscribeToEvents() {
        viewModelScope.launch {
            eventQueue.consumeAsFlow()
                .debounce(100L)
                .distinctUntilChangedBy {
                    when (it) {
                        is HomeScreenEvents.SelectCategory -> it.id
                        HomeScreenEvents.InitScreen -> Unit
                    }
                }
                .collect {
                    sendEvent(it)
                }
        }
    }

    private suspend fun sendEvent(e: HomeScreenEvents) {
        when (e) {
            is HomeScreenEvents.SelectCategory -> selectCategory(e.id)
            HomeScreenEvents.InitScreen -> _screenState.emit(
                currState.copy(
                    isLoading = true
                )
            )
        }
    }

    private suspend fun selectCategory(id: HomeGamesCategory) {
        Log.d("TAGTAG", "selectCategory")
        if (id != currState.category)
            when (id) {
                HomeGamesCategory.LatestReleases -> {
                    getLatestReleasesUseCase.invoke(id.shouldFetch).collect {
                        mapResult(it, HomeGamesCategory.LatestReleases)
                    }
                }

                HomeGamesCategory.MostRatedLastMonth -> {
                    getMostRatedLastMonthUseCase(id.shouldFetch).collect {
                        mapResult(it, HomeGamesCategory.MostRatedLastMonth)
                    }
                }

                HomeGamesCategory.UpcomingReleases -> {
                    getUpcomingReleasesUseCase(id.shouldFetch).collect {
                        mapResult(it, HomeGamesCategory.UpcomingReleases)
                    }
                }

                else -> {
                    _screenState.emit(
                        currState.copy(
                            error = Throwable("No such category")
                        )
                    )
                }
            }
        id.shouldFetch = false
    }

    private suspend fun setCategory(items: List<PreviewGameModel>, type: HomeGamesCategory) {
        Log.d("tagtag", "setting category")
        _screenState.emit(
            currState.copy(
                games = items,
                category = type,
                isLoading = false,
                areGamesLoading = false,
                error = null
            )
        )
    }

    private suspend fun mapResult(
        result: StateResult<List<PreviewGameModel>>,
        type: HomeGamesCategory
    ) {
        when (result) {
            is StateResult.Error -> {
                _screenState.emit(
                    currState.copy(
                        error = result.exception,
                        areGamesLoading = false,
                        isLoading = false
                    )
                )
            }

            StateResult.Loading -> {
                _screenState.emit(
                    currState.copy(
                        areGamesLoading = true,
                        error = null,
                        isLoading = false
                    )
                )
            }

            is StateResult.Success -> setCategory(result.data, type)
        }
    }
}
