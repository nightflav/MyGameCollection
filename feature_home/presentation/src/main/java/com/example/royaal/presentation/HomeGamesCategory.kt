package com.example.royaal.presentation

internal sealed class HomeGamesCategory(var shouldFetch: Boolean) {

    object Undefined : HomeGamesCategory(shouldFetch = false)
    object LatestReleases : HomeGamesCategory(true)
    object MostRatedLastMonth : HomeGamesCategory(true)
    object UpcomingReleases : HomeGamesCategory(true)

    override fun toString(): String {
        return when (this) {
            Undefined -> UNDEFINED
            LatestReleases -> LATEST
            MostRatedLastMonth -> MOST_RATED
            UpcomingReleases -> UPCOMING
        }
    }

    companion object {
        private const val LATEST = "Latest"
        private const val UPCOMING = "Upcoming"
        private const val MOST_RATED = "MostRated"
        private const val UNDEFINED = "Undefined"

        fun allCategories() = listOf(
            LatestReleases.toString(),
            MostRatedLastMonth.toString(),
            UpcomingReleases.toString()
        )

        fun getCategoryByName(name: String) =
            when (name) {
                LATEST -> LatestReleases
                UPCOMING -> UpcomingReleases
                MOST_RATED -> MostRatedLastMonth
                else -> throw (IllegalStateException("No such HomeGamesCategory"))
            }
    }
}