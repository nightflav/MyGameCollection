package com.example.royaal.core.common.model.uimodel

data class ProfileModel(
    val id: Int,
    val name: String,
    val secondName: String,
    val imageUrl: String
) {
    companion object {
        val emptyProfile = ProfileModel(
            id = -1,
            name = "Name",
            secondName = "Second Name",
            imageUrl = ""
        )
    }
}
