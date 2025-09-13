package com.example.vidiyakul.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vidiyakul.presentation.view.DisplayPaidBatches
import com.example.vidiyakul.presentation.view.videoplayer.VideoPlayerScreen
import com.example.vidiyakul.presentation.view.home.HomeScreen
import com.example.vidiyakul.presentation.view.nonetwork.NoInternetScreen
import com.example.vidiyakul.presentation.viewmodel.VideoPlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(viewmodel: VideoPlayerViewModel) {
    val navController = rememberNavController()


        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "videoList"
            ) {
                composable("videoList") {
                    HomeScreen(navController, viewmodel)
                }
                composable(
                    route = "videoPlayer/{videoId}",
                    arguments = listOf(navArgument("videoId") { type = NavType.StringType })
                ) { backStackEntry ->
                    val videoId = backStackEntry.arguments?.getString("videoId") ?: ""
                    VideoPlayerScreen(videoId = videoId, viewmodel, navController)
                }
                composable("noInternet") {
                    NoInternetScreen(navController)
                }
                composable("paidbatch") {
                    DisplayPaidBatches()
                }
            }
        }
    }

