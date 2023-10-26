package com.example.royaal.core.database.app

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class GamesDatabase : RoomDatabase() {

    abstract fun gamesDao(): GamesDao

}