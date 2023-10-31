package com.example.royaal.core.database.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addGame(game: GameEntity)

    @Query("delete from AppDBTable where id=:id")
    suspend fun deleteGameById(id: Int)

    @Query("select * from AppDBTable where isFavourite")
    fun getFavouriteGames(): Flow<List<GameEntity>>

    @Query("select * from AppDBTable where isCompleted")
    fun getCompletedGames(): Flow<List<GameEntity>>

    @Query("select * from AppDBTable where isInWishList")
    fun getWishListGames(): Flow<List<GameEntity>>

    @Query("select exists(select * from AppDBTable where id = :id)")
    suspend fun isGameExist(id: Int): Boolean

    @Query("select * from AppDBTable where id = :id")
    fun getGameById(id: Int): Flow<GameEntity>

    @Query("update AppDBTable set isFavourite=:newStatus where id=:id")
    suspend fun updateFavouriteStatusById(id: Int, newStatus: Boolean)

    @Query("update AppDBTable set isCompleted=:newStatus where id=:id")
    suspend fun updateCompletedStatusById(id: Int, newStatus: Boolean)

    @Query("update AppDBTable set isInWishList=:newStatus where id=:id")
    suspend fun updateWishlistStatusById(id: Int, newStatus: Boolean)

    @Query("delete from AppDBTable where not isInWishList and not isCompleted and not isFavourite")
    suspend fun clearCache()
}