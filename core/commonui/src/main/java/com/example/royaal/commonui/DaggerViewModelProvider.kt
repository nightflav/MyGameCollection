package com.example.royaal.commonui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

class DaggerViewModelProvider {

    companion object {
        @Composable
        @Suppress("unchecked_cast")
        inline fun <reified VM : ViewModel> daggerViewModel(
            key: String? = null,
            crossinline viewModelInstanceCreator: @DisallowComposableCalls () -> VM
        ): VM {
            val factory = remember(key) {
                object : ViewModelProvider.Factory {
                    override fun <VM : ViewModel> create(modelClass: Class<VM>): VM {
                        return viewModelInstanceCreator() as VM
                    }
                }
            }
            return viewModel(key = key, factory = factory)
        }
    }
}