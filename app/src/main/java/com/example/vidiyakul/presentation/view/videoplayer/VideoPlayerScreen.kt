package com.example.vidiyakul.presentation.view.videoplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.delay
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.SkipNext
import com.example.vidiyakul.presentation.viewmodel.VideoPlayerViewModel


@Composable
fun VideoPlayerScreen( videoId: String, viewModel: VideoPlayerViewModel) {
    val context = LocalContext.current

            // State variables for UI
            var isPlaying by remember { mutableStateOf(false) }
            var currentPosition by remember { mutableStateOf(0L) }
            var duration by remember { mutableStateOf(0L) }
            var showControls by remember { mutableStateOf(true) }

            // Collect last saved position from DB
            val lastSavedPosition by viewModel.lastPosition.collectAsState()

            // Ask VM to load saved progress for this videoId
            LaunchedEffect(videoId) {
                viewModel.loadProgress(videoId)
            }

            // Create the video player
            val exoPlayer = remember {
                ExoPlayer.Builder(context).build().apply {
                    val mediaItem = MediaItem.fromUri(
                        "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.mp4/.m3u8"
                    )
                    setMediaItem(mediaItem)

                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            if (playbackState == Player.STATE_READY) {
                                duration = this@apply.duration
                                // Seek to saved position only once when video is ready
                                if (lastSavedPosition > 0) {
                                    seekTo(lastSavedPosition)
                                }
                            }
                        }

                        override fun onIsPlayingChanged(playing: Boolean) {
                            isPlaying = playing
                        }
                    })

                    prepare()
                    playWhenReady = true
                }
            }

            // Update current position every second
            LaunchedEffect(exoPlayer) {
                while (true) {
                    currentPosition = exoPlayer.currentPosition
                    delay(1000)
                }
            }

            // Save progress + release player when leaving
            DisposableEffect(exoPlayer) {
                onDispose {
                    viewModel.saveProgress(videoId, exoPlayer.currentPosition)
                    exoPlayer.release()
                }
            }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Video Player Container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            // Video Player
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false // We'll make our own controls
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            // Custom Controls Overlay
            if (showControls) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Play/Pause Button
                    IconButton(
                        onClick = {
                            if (isPlaying) {
                                exoPlayer.pause()
                            } else {
                                exoPlayer.play()
                            }
                        },
                        modifier = Modifier.size(64.dp)
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }

        // Progress and Controls Section
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Time Display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formatTime(currentPosition),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = formatTime(duration),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress Bar
                if (duration > 0) {
                    Slider(
                        value = currentPosition.toFloat(),
                        onValueChange = { newPosition ->
                            exoPlayer.seekTo(newPosition.toLong())
                        },
                        valueRange = 0f..duration.toFloat(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Control Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Rewind (using SkipPrevious icon)
                    IconButton(onClick = {
                        val newPosition = (currentPosition - 10000).coerceAtLeast(0)
                        exoPlayer.seekTo(newPosition)
                    }) {
                        Icon(Icons.Default.SkipPrevious, "Rewind 10s")
                    }

                    // Play/Pause
                    IconButton(onClick = {
                        if (isPlaying) {
                            exoPlayer.pause()
                        } else {
                            exoPlayer.play()
                        }
                    }) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play"
                        )
                    }

                    // Forward (using SkipNext icon)
                    IconButton(onClick = {
                        val newPosition = (currentPosition + 10000).coerceAtMost(duration)
                        exoPlayer.seekTo(newPosition)
                    }) {
                        Icon(Icons.Default.SkipNext, "Forward 10s")
                    }

                    // Fullscreen (using AspectRatio icon)
                    IconButton(onClick = {
                        println("Fullscreen clicked!")
                    }) {
                        Icon(Icons.Default.AspectRatio, "Fullscreen")
                    }
                }
            }
        }

        // Status Text
        Text(
            text = "✅ Advanced Video Player Ready!",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Helper function to format time (milliseconds to MM:SS)
private fun formatTime(timeMs: Long): String {
    val totalSeconds = timeMs / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}