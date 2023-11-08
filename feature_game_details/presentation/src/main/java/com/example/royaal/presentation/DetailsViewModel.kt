package com.example.royaal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royaal.core.common.fetchResult
import com.example.royaal.domain.usecases.AddToCompletedUseCase
import com.example.royaal.domain.usecases.AddToFavouriteUseCase
import com.example.royaal.domain.usecases.AddToWishListUseCase
import com.example.royaal.domain.usecases.LoadScreenUseCase
import com.example.royaal.domain.usecases.LoadSimilarGamesUseCase
import com.example.royaal.domain.usecases.RemoveGameFromCompletedUseCase
import com.example.royaal.domain.usecases.RemoveGameFromFavouriteUseCase
import com.example.royaal.domain.usecases.RemoveGameFromWishListUseCase
import com.example.royaal.presentation.DetailsScreenState.Companion.game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DetailsViewModel @Inject constructor(
    private val loadScreenUseCase: LoadScreenUseCase,
    private val loadSimilarGamesUseCase: LoadSimilarGamesUseCase,
    private val addToCompletedUseCase: AddToCompletedUseCase,
    private val addToFavouriteUseCase: AddToFavouriteUseCase,
    private val addToWishListUseCase: AddToWishListUseCase,
    private val removeGameFromCompletedUseCase: RemoveGameFromCompletedUseCase,
    private val removeGameFromFavouriteUseCase: RemoveGameFromFavouriteUseCase,
    private val removeGameFromWishListUseCase: RemoveGameFromWishListUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState.EmptyState)
    val state = _state.asStateFlow()
    private val currState get() = state.value

    sealed interface DetailsScreenEvents {
        data class LoadScreen(val id: Int) : DetailsScreenEvents
        data class OpenScreenshot(val url: String) : DetailsScreenEvents
        data object AddToFavourite : DetailsScreenEvents
        data object AddToWishlist : DetailsScreenEvents
        data object AddToCompleted : DetailsScreenEvents
        data object LoadSimilarGames : DetailsScreenEvents
    }

    fun sendEvent(e: DetailsScreenEvents) {
        viewModelScope.launch {
            when (e) {
                is DetailsScreenEvents.OpenScreenshot -> {

                }

                DetailsScreenEvents.AddToCompleted -> {
                    addToCompleted()
                }

                DetailsScreenEvents.AddToFavourite -> {
                    addToFavourite()
                }

                DetailsScreenEvents.AddToWishlist -> {
                    addToWishlist()
                }

                is DetailsScreenEvents.LoadScreen -> {
                    loadScreen(e.id)
                }

                DetailsScreenEvents.LoadSimilarGames -> {
                    loadSimilarGames()
                }
            }
        }
    }

    private suspend fun addToWishlist() {
        if (currState.isInWishList) {
            removeGameFromWishListUseCase(currState.gameId)
        } else {
            addToWishListUseCase(currState.game)
        }
    }

    private suspend fun addToCompleted() {
        if (currState.isCompleted) {
            removeGameFromCompletedUseCase(currState.gameId)
        } else {
            addToCompletedUseCase(currState.game)
        }
    }

    private suspend fun addToFavourite() {
        if (currState.isFavourite) {
            removeGameFromFavouriteUseCase(currState.gameId)
        } else {
            addToFavouriteUseCase(currState.game)
        }
    }

    private suspend fun loadSimilarGames() {
        loadSimilarGamesUseCase(currState.gameId).collect {
            it.fetchResult(
                onLoading = {
                    _state.emit(
                        currState.copy(
                            similarGameLoading = true
                        )
                    )
                },
                onError = { error ->
                    _state.emit(
                        currState.copy(
                            error = error,
                            similarGameLoading = false
                        )
                    )
                },
                onSuccess = { similarGames ->
                    _state.emit(
                        currState.copy(
                            similarGames = similarGames,
                            similarGameLoading = false
                        )
                    )
                }
            )
        }
    }

    private suspend fun loadScreen(id: Int) {
        loadScreenUseCase(id).collect {
            it.fetchResult(
                onLoading = {
                    _state.emit(
                        currState.loading.copy(
                            gameId = id
                        )
                    )
                },
                onError = { error ->
                    _state.emit(
                        currState.copy(
                            error = error
                        )
                    )
                },
                onSuccess = { game ->
                    _state.emit(
                        currState.copy(
                            gameId = game.id,
                            title = game.name,
                            description = game.description,
                            backgroundImg = game.backgroundUrl,
                            screenshots = game.screenshots,
                            platforms = game.platforms,
                            isLoading = false,
                            error = null,
                            isCompleted = game.isCompleted,
                            isFavourite = game.isFavourite,
                            isInWishList = game.isInWishlist
                        )
                    )
                }
            )
        }
    }

    private val DetailsScreenState.loading
        get() = this.copy(
            isLoading = true
        )
}