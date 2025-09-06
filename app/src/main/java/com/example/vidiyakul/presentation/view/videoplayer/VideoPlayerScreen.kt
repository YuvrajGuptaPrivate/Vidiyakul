package com.example.vidiyakul.presentation.view.videoplayer

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.vidiyakul.data.isInternetAvailable
import com.example.vidiyakul.presentation.view.widgets.CommentItem
import com.example.vidiyakul.presentation.viewmodel.VideoPlayerViewModel
import kotlinx.coroutines.launch

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerScreen(videoId: String, viewModel: VideoPlayerViewModel,navController : NavController) {
    val context = LocalContext.current
    val activity = context as Activity
    val configuration = LocalConfiguration.current
    val isLoading by viewModel.isLoading.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val playbackError by viewModel.playbackError.collectAsState()

    var commentText by remember { mutableStateOf("") }

    val video = remember(videoId) { viewModel.getVideoById(videoId) }

    val isFullscreen by viewModel.isFullscreen.collectAsState()
    val isLandscape =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(videoId) {
        viewModel.loadProgress(videoId)
    }

    LaunchedEffect(Unit) {
        if (!isInternetAvailable(context)) {
            navController.navigate("noInternet") {
            }
        }
    }

    LaunchedEffect(playbackError) {
        if (playbackError) {
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = "Something went wrong with this video. Please retry.",
                    actionLabel = "Retry",
                    duration = SnackbarDuration.Long
                )
                if (result == SnackbarResult.ActionPerformed) {
                    viewModel.retryPlayback(videoId, context)
                }
            }
        }
    }
    LaunchedEffect(isFullscreen) {
        if (isFullscreen) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE

            WindowCompat.setDecorFitsSystemWindows(activity.window, false)
            WindowInsetsControllerCompat(
                activity.window,
                activity.window.decorView
            ).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            WindowCompat.setDecorFitsSystemWindows(activity.window, true)
            WindowInsetsControllerCompat(activity.window, activity.window.decorView).show(
                WindowInsetsCompat.Type.systemBars()
            )
        }
    }

    LaunchedEffect(isLandscape) {
        if (isLandscape && !isFullscreen) {
            viewModel.setFullscreen(true)
        } else if (!isLandscape && isFullscreen) {
            viewModel.setFullscreen(false)
        }
    }

    val exoPlayer = viewModel.getOrCreateExoPlayer(context, videoId)

    val playerView = remember(videoId) {
        PlayerView(context).apply {
            player = exoPlayer
            useController = true
            controllerShowTimeoutMs = 3000

           resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

            setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
            setFullscreenButtonClickListener { isFullscreenRequested ->
                viewModel.setFullscreen(isFullscreenRequested)
            }
        }
    }

    DisposableEffect(videoId) {
        onDispose {
            viewModel.saveProgress(videoId, viewModel.getCurrentPosition())
            viewModel.releasePlayer()

            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            WindowCompat.setDecorFitsSystemWindows(activity.window, true)
            WindowInsetsControllerCompat(activity.window, activity.window.decorView).show(
                WindowInsetsCompat.Type.systemBars()
            )
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        if (isFullscreen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .zIndex(Float.MAX_VALUE)
                    .padding(innerPadding)
            ) {
                AndroidView(
                    factory = { playerView },
                    modifier = Modifier.fillMaxSize()
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()
                .padding(innerPadding)
            ) {
                Box {
                    AndroidView(
                        factory = { playerView },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)

                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = MaterialTheme.colorScheme.primary

                        )
                    }
                }


                video?.let { videoObj ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = videoObj.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant

                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = videoObj.description,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f) // A suitable themed faded text color
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Like",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("3.6k", style = MaterialTheme.typography.bodyMedium)
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Comment,
                                        contentDescription = "Comments",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("233", style = MaterialTheme.typography.bodyMedium)
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                IconButton(onClick = { }) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Save",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Comments",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface

                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            LazyColumn(
                                modifier = Modifier.heightIn(max = 150.dp)
                            ) {
                                items(videoObj.comments) { comment ->
                                    CommentItem(comment, modifier = Modifier)
                                    Spacer(modifier = Modifier.height(12.dp))

                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = commentText,
                                    onValueChange = { commentText = it },
                                    placeholder = {
                                        Text(
                                            "Add a comment",
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    singleLine = true,


                                    )

                                IconButton(
                                    onClick = {
                                        if (commentText.isNotBlank()) {

                                            commentText = ""
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Send,
                                        tint = MaterialTheme.colorScheme.primary,
                                        contentDescription = "Send Comment"

                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}
