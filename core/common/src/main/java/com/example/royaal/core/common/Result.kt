package com.example.royaal.core.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface StateResult<out T> {
    data class Success<T>(val data: T) : StateResult<T>
    data class Error(val exception: Throwable? = null) : StateResult<Nothing>
    data object Loading : StateResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<StateResult<T>> {
    return this
        .map<T, StateResult<T>> {
            StateResult.Success(it)
        }
        .onStart { emit(StateResult.Loading) }
        .catch { emit(StateResult.Error(it)) }
}

suspend fun <T> StateResult<T>.fetchResult(
    onLoading: suspend () -> Unit,
    onError: suspend (Throwable?) -> Unit,
    onSuccess: suspend (T) -> Unit
) {
    when (this) {
        is StateResult.Error -> onError(exception)
        StateResult.Loading -> onLoading()
        is StateResult.Success -> onSuccess(data)
    }
}