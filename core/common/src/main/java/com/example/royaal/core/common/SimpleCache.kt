package com.example.royaal.core.common

interface SimpleCache<T> {

    fun checkIfCached(key: Any): Boolean

    fun getByKey(key: Any): T

    fun cacheData(key: Any, data: T)
}

class SimpleCacheImpl<T> : SimpleCache<T> {

    private val simpleCache: MutableMap<Any, T> = mutableMapOf()

    override fun checkIfCached(key: Any): Boolean = simpleCache[key] != null

    override fun getByKey(key: Any): T = if (simpleCache[key]!=null) simpleCache[key]!! else key as T

    override fun cacheData(key: Any, data: T) {
        simpleCache[key] = data
    }
}