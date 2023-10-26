package com.example.royaal.mygamecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.example.royaal.data.UserSettingsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

interface SavedStateViewModelFactory<T : ViewModel> {

    fun create(savedStateHandle: SavedStateHandle): T
}

class MainActivityViewModel @AssistedInject constructor(
    userSettingsRepo: UserSettingsRepository,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = userSettingsRepo.getUserSettings().map {
        MainActivityUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(5000)
    )

    @AssistedFactory
    interface Factory : SavedStateViewModelFactory<MainActivityViewModel>

}

inline fun <reified VM : ViewModel> ComponentActivity.assistedViewModel(
    crossinline viewModelProducer: (SavedStateHandle) -> VM,
): Lazy<VM> = lazy {
    ViewModelProvider(this, createSavedStateViewModelFactory(intent.extras, viewModelProducer))[VM::class.java]
}

@PublishedApi
internal inline fun <reified T : ViewModel> SavedStateRegistryOwner.createSavedStateViewModelFactory(
    arguments: Bundle?,
    crossinline creator: (SavedStateHandle) -> T,
): ViewModelProvider.Factory = object :
    AbstractSavedStateViewModelFactory(this@createSavedStateViewModelFactory, arguments) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = creator(handle) as T
}