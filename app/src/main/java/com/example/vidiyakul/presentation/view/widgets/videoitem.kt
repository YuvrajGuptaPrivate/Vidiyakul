package com.example.vidiyakul.presentation.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.vidiyakul.data.model.Video

@Composable
fun VideoItem(video: Video, modifier: Modifier = Modifier) {
    Column(modifier = modifier.width(200.dp)) {

        Image(
            painter = rememberAsyncImagePainter(video.thumbnail),
            contentDescription = video.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = video.name,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = video.totalTime,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

