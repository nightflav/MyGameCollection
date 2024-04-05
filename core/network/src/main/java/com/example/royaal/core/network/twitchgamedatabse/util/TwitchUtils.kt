package com.example.royaal.core.network.twitchgamedatabse.util

import com.example.royaal.core.network.twitchgamedatabse.util.TwitchUtils.Game.Sort.Desc.parse

class TwitchUtils {
    sealed class GameCategory(val code: Int, val slug: String) {
        data object MainGame : GameCategory(0, "main_game")
        data object DlcAddon : GameCategory(1, "dlc_addon")
        data object Expansion : GameCategory(2, "expansion")
        data object Bundle : GameCategory(3, "bundle")
        data object StandaloneExpansion : GameCategory(4, "standalone_expansion")
        data object Mod : GameCategory(5, "mod")
        data object Episode : GameCategory(6, "episode")
        data object Season : GameCategory(7, "season")
        data object Remake : GameCategory(8, "remake")
        data object Remaster : GameCategory(9, "remaster")
        data object ExpandedGame : GameCategory(10, "expanded_game")
        data object Port : GameCategory(11, "port")
        data object Fork : GameCategory(12, "fork")
        data object Pack : GameCategory(13, "pack")
        data object Update : GameCategory(14, "update")
    }

    sealed class GameStatus(val code: Int, val slug: String) {
        data object Released : GameStatus(0, "released")
        data object Alpha : GameStatus(2, "alpha")
        data object Beta : GameStatus(3, "beta")
        data object EarlyAccess : GameStatus(4, "early_access")
        data object Offline : GameStatus(5, "offline")
        data object Cancelled : GameStatus(6, "cancelled")
        data object Rumored : GameStatus(7, "rumored")
        data object Delisted : GameStatus(8, "delisted")
    }

    class Game {

        /**
         * Equals => release date equals date
         *
         * More => release date less that date
         *
         * Less => release date more than date
         */
        sealed class DateParameter(open val date: String) {
            data class Equals(override val date: String) : DateParameter(date)
            data class More(override val date: String) : DateParameter(date)
            data class Less(override val date: String) : DateParameter(date)

            fun parse() = when (this) {
                is Equals -> "=$date & "
                is Less -> "<$date & "
                is More -> ">$date & "
            }

        }

        sealed class Sort(private val sorting: String) {
            data object Asc : Sort("asc")
            data object Desc : Sort("desc")

            fun Sort.parse(sortBy: String?) = if (sortBy != null) "sort $sortBy $sorting;" else ""
        }

        data class Parameters(
            val genreId: Int? = null,
            val collectionId: Int? = null,
            val franchiseId: Int? = null,
            val fromDate: DateParameter? = null,
            val toDate: DateParameter? = null,
            val alternativeName: Int? = null,
            val platform: Int? = null,
            val status: Int? = null,
        ) {

            private fun any(): Boolean =
                genreId != null || collectionId != null || franchiseId != null ||
                        fromDate != null || toDate != null || alternativeName != null ||
                        platform != null || status != null

            private val mapParams = mapOf(
                "genres" to genreId,
                "collections" to collectionId,
                "franchises" to franchiseId,
                "alternative_names" to alternativeName,
                "platforms" to platform,
                "status" to status
            )

            fun generate() = if (any()) "where " +
                    (toDate?.parse() ?: "") +
                    (fromDate?.parse() ?: "") +
                    (mapParams.asSequence().joinToString("") { entry ->
                        if (entry.value != null)
                            "${entry.key}=${entry.value} & "
                        else
                            ""
                    }).dropLast(3) + ";" else null
        }

        private fun String.addBodyParameters(
            query: String? = null,
            parameters: Parameters? = null,
            manualParams: Map<String, Any>? = null
        ): String {
            if (query != null) return query
            val pQuery = parameters?.generate()
            return this + (if (pQuery != null) {
                "$pQuery"
            } else if (manualParams != null) {
                generate(manualParams)
            } else "")
        }

        private fun String.addSorting(
            sortBy: String? = null,
            sorting: Sort? = null
        ): String = this + (sorting?.parse(sortBy) ?: "")

        /**
         * Use this method to create a proper body with required fields
         * like genre, collection, genre and so on.
         *
         * You should use ID's from IGDB to set genre, collection, etc.
         *
         * In case you wanna use words to set fields in body string values will be overriden
         * so if you pass id and actual value this method will use id's.
         *
         * By passing an entire body, which is also possible, you use it. But there is no reason to do this.
         *
         * You also should care about what exactly you pass.
         *
         * When passing manualParams you should define operation by yourself. That means that
         * you should define it such way: mapOf("genres.name=", "RGP")
         */
        fun getBody(
            query: String? = null,
            parameters: Parameters? = null,
            manualParams: Map<String, Any>? = null,
            sort: Map<Sort, String>? = null,
            uniqueFields: String? = null
        ): String = ("fields *" + if (uniqueFields != null) ",$uniqueFields;" else ";")
            .addBodyParameters(query, parameters, manualParams)
            .apply {
                sort?.forEach { e ->
                    this.addSorting(e.value, e.key)
                }
            }

        private fun generate(mapParams: Map<String, Any>) =
            "where " + (mapParams.asSequence().joinToString("") { entry ->
                "${entry.key}${entry.value} & "
            }).dropLast(3) + ";"
    }
}