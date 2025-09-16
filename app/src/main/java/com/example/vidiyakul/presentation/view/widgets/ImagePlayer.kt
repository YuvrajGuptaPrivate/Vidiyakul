package com.example.vidiyakul.presentation.view.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable

fun ImagePlayer(imageUrl  : String)
{

    val thumbnailUrl = "https://img.youtube.com/vi/${imageUrl}/hqdefault.jpg"

    Image(
        painter = rememberAsyncImagePainter(thumbnailUrl),
        contentDescription = "Images for Promoting new features",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .width(400.dp)
            .height(360.dp)
            .fillMaxWidth()
    )

}