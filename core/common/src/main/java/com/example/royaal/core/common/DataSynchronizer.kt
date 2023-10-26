package com.example.royaal.core.common

import com.example.royaal.core.common.model.DatabaseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class DataSynchronizer {
    companion object {
        suspend fun <T : DatabaseItem> synchronizeListOfData(
            cached: suspend () -> List<T>,
            from: suspend () -> List<T>,
            to: suspend (List<T>) -> Unit,
            clearPrevious: suspend () -> Unit,
            shouldFetch: Boolean = true
        ): Flow<List<T>> {
            val cache = cached()
            return flow {
                if (shouldFetch) {
                    val newData = from()
                    clearPrevious()
                    to(newData)
                    emit(newData)
                } else
                    emit(cache)
            }.catch {
                if (cache.isNotEmpty())
                    emit(cache)
                throw it
            }
        }
    }
}