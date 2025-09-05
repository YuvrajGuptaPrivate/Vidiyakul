package com.example.vidiyakul.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vidiyakul.data.model.VideoProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoProgressDao {

    @Query("SELECT * FROM video_progress WHERE videoId = :videoId")
    suspend fun getProgressByVideoId(videoId: String): VideoProgressEntity?

    @Query("SELECT * FROM video_progress WHERE videoId = :videoId")
    fun getProgressFlow(videoId: String): Flow<VideoProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(videoProgress: VideoProgressEntity)

    @Query("DELETE FROM video_progress WHERE videoId = :videoId")
    suspend fun deleteProgress(videoId: String)

    @Query("SELECT * FROM video_progress")
    suspend fun getAllProgress(): List<VideoProgressEntity>
}