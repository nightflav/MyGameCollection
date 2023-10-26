package com.example.royaal.core.database

import androidx.room.TypeConverter
import com.example.royaal.core.database.app.PlatformDb
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromStringList(value: List<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String) = Json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromPlatformList(value: List<PlatformDb>) = Json.encodeToString(value)

    @TypeConverter
    fun toPlatformList(value: String) = Json.decodeFromString<List<PlatformDb>>(value)

}