package com.example.vidiyakul.presentation.viewmodel


import VideoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VideoPlayerViewModelFactory(
    private val repository: VideoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoPlayerViewModel::class.java)) {
            return VideoPlayerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}