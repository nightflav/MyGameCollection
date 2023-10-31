package com.example.royaal.presentation

internal sealed class FavouriteScreenCategory {

    object Undefined : FavouriteScreenCategory()
    object Favourite : FavouriteScreenCategory()
    object Completed : FavouriteScreenCategory()
    object Wishlist : FavouriteScreenCategory()

    override fun toString(): String {
        return when (this) {
            Undefined -> UNDEFINED
            Favourite -> FAVOURITE
            Completed -> COMPLETED
            Wishlist -> WISHLIST
        }
    }

    companion object {
        private const val FAVOURITE = "Favourite"
        private const val COMPLETED = "Completed"
        private const val WISHLIST = "Wishlist"
        private const val UNDEFINED = "Undefined"

        fun allCategories() = listOf(
            Favourite.toString(),
            Completed.toString(),
            Wishlist.toString()
        )

        fun getCategoryByName(name: String) =
            when (name) {
                FAVOURITE -> Favourite
                COMPLETED -> Completed
                WISHLIST -> Wishlist
                else -> throw (IllegalStateException("No such HomeGamesCategory"))
            }
    }
}