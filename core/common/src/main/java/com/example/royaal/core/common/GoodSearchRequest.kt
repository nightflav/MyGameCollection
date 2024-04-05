package com.example.royaal.core.common

fun String.isSearchRequest(query: String) =
    contains(query, true) || query.contains(this, true)

fun String.isSearchRequestForAny(vararg queries: String) =
    queries.any { isSearchRequest(it) || it.isSearchRequest(this) }