package com.example.royaal.core.database.di

import androidx.compose.runtime.compositionLocalOf
import com.example.royaal.core.database.app.GamesDao

interface DatabaseProvider {

    val gamesDao: GamesDao

}

val LocalDatabaseProvider = compositionLocalOf<DatabaseProvider> {
    error("No database provided")
}