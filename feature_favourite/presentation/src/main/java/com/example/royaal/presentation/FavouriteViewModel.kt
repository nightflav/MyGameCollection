package com.example.royaal.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.royaal.core.common.StateResult
import com.example.royaal.core.common.fetchResult
import com.example.royaal.core.common.model.uimodel.DetailsGameModel
import com.example.royaal.domain.usecases.SelectCompletedCategoryUseCase
import com.example.royaal.domain.usecases.SelectFavouriteCategoryUseCase
import com.example.royaal.domain.usecases.SelectWishlistCategoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class FavouriteViewModel @Inject constructor(
    private val selectFavouriteCategoryUseCase: SelectFavouriteCategoryUseCase,
    private val selectCompletedCategoryUseCase: SelectCompletedCategoryUseCase,
    private val selectWishlistCategoryUseCase: SelectWishlistCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteScreenState())
    val state = _state.asStateFlow()
    private val currState
        get() = state.value

    init {
        sendEvent(
            FavouriteScreenEvents.SelectCategory(
                FavouriteScreenCategory.Favourite
            )
        )
    }

    sealed class FavouriteScreenEvents {
        data class SelectCategory(val newCategory: FavouriteScreenCategory) :
            FavouriteScreenEvents()
    }

    fun sendEvent(e: FavouriteScreenEvents) {
        viewModelScope.launch {
            when (e) {
                is FavouriteScreenEvents.SelectCategory -> {
                    selectCategory(e.newCategory)
                }
            }
        }
    }

    private suspend fun selectCategory(newCategory: FavouriteScreenCategory) {
        when (newCategory) {
            FavouriteScreenCategory.Completed -> {
                selectCompletedCategoryUseCase().collect {
                    setGames(it, newCategory)
                }
            }

            FavouriteScreenCategory.Favourite -> {
                selectFavouriteCategoryUseCase().collect {
                    setGames(it, newCategory)
                }
            }

            FavouriteScreenCategory.Wishlist -> {
                selectWishlistCategoryUseCase().collect {
                    setGames(it, newCategory)
                }
            }

            FavouriteScreenCategory.Undefined -> _state.emit(
                currState.copy(
                    isLoading = true
                )
            )
        }
    }

    private suspend fun setGames(
        games: StateResult<List<DetailsGameModel>>,
        newCategory: FavouriteScreenCategory
    ) {
        _state.emit(
            currState.copy(
                selectedCategory = newCategory
            )
        )
        games.fetchResult(
            onLoading = {
                _state.emit(
                    currState.copy(
                        areGamesLoading = true,
                        error = null,
                        isLoading = false
                    )
                )
            },
            onSuccess = {
                _state.emit(
                    currState.copy(
                        games = it,
                        error = null,
                        isLoading = false,
                        areGamesLoading = false
                    )
                )
            },
            onError = {
                _state.emit(
                    currState.copy(
                        error = it,
                        isLoading = false,
                        areGamesLoading = false
                    )
                )
            }
        )
    }

}