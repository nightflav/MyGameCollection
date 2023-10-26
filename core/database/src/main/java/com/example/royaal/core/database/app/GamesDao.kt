package com.example.royaal.core.database.app

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(
        game: GameEntity
    )

    @Delete
    suspend fun deleteGame(
        game: GameEntity
    )

    @Query("SELECT * FROM AppDBTable WHERE isFavourite")
    suspend fun getFavouriteGames(): List<GameEntity>

    @Query("SELECT * FROM AppDBTable WHERE isFavourite")
    suspend fun getCompletedGames(): List<GameEntity>

    @Query("SELECT * FROM AppDBTable WHERE isFavourite")
    suspend fun getWishListGames(): List<GameEntity>

    @Query("SELECT EXISTS(SELECT * FROM AppDBTable WHERE id = :id)")
    suspend fun isGameIsExist(id: Int): Boolean

    @Query("SELECT * FROM AppDBTable WHERE id = :id")
    suspend fun getGameById(id: Int): GameEntity

    @Update
    suspend fun updateGameById(game: GameEntity)
}