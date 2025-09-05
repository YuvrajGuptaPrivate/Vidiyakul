package com.example.vidiyakul.presentation

import VideoRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.room.Room
import com.example.vidiyakul.data.db.AppDatabase
import com.example.vidiyakul.presentation.theme.VidiyakulTheme
import com.example.vidiyakul.presentation.viewmodel.VideoPlayerViewModel
import com.example.vidiyakul.presentation.viewmodel.VideoPlayerViewModelFactory

class MainActivity : ComponentActivity() {

    private val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "video_db"
        ).build()
    }

    private val repository by lazy {
        VideoRepository(database.videoProgressDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val videoplayer: VideoPlayerViewModel by viewModels {
            VideoPlayerViewModelFactory(repository)
        }

        setContent {
            VidiyakulTheme {
                MyApp(videoplayer)
            }
        }
    }
}