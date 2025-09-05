package com.example.vidiyakul.presentation.viewmodel

import VideoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidiyakul.data.model.VideoProgressEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class VideoPlayerViewModel(
    private val repository: VideoRepository
) : ViewModel() {

    private val _lastPosition = MutableStateFlow(0L)
    val lastPosition: StateFlow<Long> = _lastPosition.asStateFlow()

    fun loadProgress(videoId: String) {
        viewModelScope.launch {
            try {
                val progress = repository.getProgress(videoId)
                _lastPosition.value = progress?.lastPosition ?: 0L
            } catch (e: Exception) {
                _lastPosition.value = 0L
            }
        }
    }

    fun saveProgress(videoId: String, position: Long) {
        viewModelScope.launch {
            try {
                val videoProgress = VideoProgressEntity(
                    videoId = videoId,
                    lastPosition = position
                )
                repository.saveProgress(videoProgress)
            } catch (e: Exception) {
                // Handle error silently or log it
            }
        }
    }
}