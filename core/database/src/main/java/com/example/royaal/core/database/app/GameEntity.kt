package com.example.royaal.core.database.app

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.royaal.core.common.model.DatabaseItem
import com.example.royaal.core.database.Converters

@Entity(
    tableName = "AppDBTable"
)
@TypeConverters(Converters::class)
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val platforms: List<PlatformDb>,
    val backgroundUrl: String,
    val description: String,
    val screenshots: List<String>,
    val isFavourite: Boolean,
    val isCompleted: Boolean,
    val isInWishList: Boolean
) : DatabaseItem
