package com.example.vidiyakul.presentation.view.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vidiyakul.data.model.Video
import com.example.vidiyakul.presentation.view.widgets.VideoItem

@Composable
fun HomeScreen(navController: NavHostController) {
    val videos = listOf(
        Video(
            id = "1",
            thumbnail = "https://img.youtube.com/vi/dA_IdSA_K5k/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "Teachers’ Day 2025 Special | Guru Ji vs Guru Ji Face-Off Must Watch!",
            totalTime = "32:42"
        ),
        Video(
            id = "2",
            thumbnail = "https://img.youtube.com/vi/rJeoCM16xbc/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "September se Padhkar Board Exam 2026 Me 95% Kaise Laye ?",
            totalTime = "59:00"
        ),
        Video(
            id = "3",
            thumbnail = "https://img.youtube.com/vi/eieVK3j7hrE/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "साहित्यिक परिचय सभी लेखकों का | Class 12th Hindi ",
            totalTime = "59:00"
        ),
        Video(
            id = "4",
            thumbnail = "https://img.youtube.com/vi/j6E2mQrtuzY/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "पंचलाइट कहानी का सारांश | Panchlight Kahani Ka Saransh Class 12th Hindi ",
            totalTime = "59:00"
        ),
        Video(
            id = "5",
            thumbnail = "https://img.youtube.com/vi/V5rGJKRihAw/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "साहित्यिक परिचय कैसे लिखे | Class 12th",
            totalTime = "59:00"
        ),
        Video(
            id = "6",
            thumbnail = "https://img.youtube.com/vi/VsInPyzdJ6k/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "Class 12th Hindi Padya Sahity Ka Vikas 50 MCQ Questions",
            totalTime = "59:00"
        )
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(videos) { video ->
            VideoItem(
                video = video,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("videoPlayer/${video.id}")
                    }
                    .padding(16.dp)
            )
        }
    }
}
