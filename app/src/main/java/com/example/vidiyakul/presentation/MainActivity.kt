package com.example.vidiyakul.presentation

import VideoRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.room.Room
import com.example.vidiyakul.data.db.AppDatabase
import com.example.vidiyakul.presentation.theme.VideoPlayerTheme
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


        val videoplayer: VideoPlayerViewModel by viewModels {
            VideoPlayerViewModelFactory(repository)
        }

        setContent {
            VideoPlayerTheme {
                val primaryColor = MaterialTheme.colorScheme.primary

                SideEffect {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.light(
                            primaryColor.toArgb(),
                            primaryColor.toArgb()
                        ),
                        navigationBarStyle = SystemBarStyle.light(
                            primaryColor.toArgb(),
                            primaryColor.toArgb()
                        )
                    )
                }
                MyApp(videoplayer)

            }
        }
    }
}