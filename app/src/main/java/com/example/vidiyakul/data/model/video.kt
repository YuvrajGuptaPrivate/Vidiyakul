package com.example.vidiyakul.data.model


data class Comment(
    val user: String,
    val text: String
)

data class Video(
    val id: String,
    val thumbnail: String,
    val url: String,
    val name: String,
    val totalTime: String,
    val description: String,
    val comments: List<Comment>
)
