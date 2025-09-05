package com.example.vidiyakul.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vidiyakul.data.dao.VideoProgressDao
import com.example.vidiyakul.data.model.VideoProgressEntity

@Database(
    entities = [VideoProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoProgressDao(): VideoProgressDao
}