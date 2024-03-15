package com.example.royaal.core.common.model.uimodel

data class PreviewGameModel(
    val id: Int,
    val name: String,
    val posterUrl: String,
    val releaseDate: String? = null,
    val metacritic: String? = null
) : UiModel
