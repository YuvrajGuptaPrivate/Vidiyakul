package com.example.vidiyakul.presentation.viewmodel

import VideoRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.vidiyakul.data.model.Comment
import com.example.vidiyakul.data.model.Video
import com.example.vidiyakul.data.model.VideoProgressEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VideoPlayerViewModel(val repository: VideoRepository) : ViewModel() {

    private val _lastPosition = MutableStateFlow(0L)
    val lastPosition: StateFlow<Long> = _lastPosition.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isFullscreen = MutableStateFlow(false)
    val isFullscreen: StateFlow<Boolean> = _isFullscreen.asStateFlow()
    private val _playbackError = MutableStateFlow(false)
    val playbackError: StateFlow<Boolean> = _playbackError.asStateFlow()

    private var currentPlaybackPosition = 0L
    private var _exoPlayer: ExoPlayer? = null
    private var _currentVideoId: String? = null
    private var hasSoughtToSavedPosition = false

    private val _videos = MutableStateFlow<List<Video>>(sampleVideos())
    val videos: StateFlow<List<Video>> = _videos

    private fun sampleVideos() = listOf(
        Video(
            id = "1",
            thumbnail = "https://img.youtube.com/vi/dA_IdSA_K5k/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "Teachers’ Day 2025 Special | Guru Ji vs Guru Ji Face-Off Must Watch!",
            totalTime = "32:42",
            description = "Face-off between two Guru Ji on Teachers’ Day 2025. Must Watch for board students!",
            comments = listOf(
                Comment(user = "Aman", text = "Amazing video for students!"),
                Comment(user = "Priya", text = "Very informative and inspiring!")
            )
        ),
        Video(
            id = "2",
            thumbnail = "https://img.youtube.com/vi/rJeoCM16xbc/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "September se Padhkar Board Exam 2026 Me 95% Kaise Laye ?",
            totalTime = "59:00",
            description = "Tips and strategies for scoring 95% in Board Exams starting your preparation from September.",
            comments = listOf(
                Comment(user = "Rahul", text = "Will follow these tips for my board prep!"),
                Comment(user = "Swati", text = "Thank you for the motivation!")
            )
        ),
        Video(
            id = "3",
            thumbnail = "https://img.youtube.com/vi/eieVK3j7hrE/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "साहित्यिक परिचय सभी लेखकों का | Class 12th Hindi ",
            totalTime = "59:00",
            description = "Comprehensive literary introduction of all authors for Class 12th Hindi.",
            comments = listOf(
                Comment(user = "Neha", text = "Helped in my exam preparation."),
                Comment(user = "Vivek", text = "Great explanation!")
            )
        ),
        Video(
            id = "4",
            thumbnail = "https://img.youtube.com/vi/j6E2mQrtuzY/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "पंचलाइट कहानी का सारांश | Panchlight Kahani Ka Saransh Class 12th Hindi ",
            totalTime = "59:00",
            description = "Detailed summary of Panchlight Kahani for Class 12th Hindi.",
            comments = listOf(
                Comment(user = "Komal", text = "Simple language and easy to understand."),
                Comment(user = "Aditya", text = "Very useful for last-minute revision!")
            )
        ),
        Video(
            id = "5",
            thumbnail = "https://img.youtube.com/vi/V5rGJKRihAw/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "साहित्यिक परिचय कैसे लिखे | Class 12th",
            totalTime = "59:00",
            description = "Guide to writing literary introductions for Class 12th Hindi examination.",
            comments = listOf(
                Comment(user = "Pooja", text = "Easy tips for exam writing."),
                Comment(user = "Karan", text = "Very clear explanation.")
            )
        ),
        Video(
            id = "6",
            thumbnail = "https://img.youtube.com/vi/VsInPyzdJ6k/hqdefault.jpg",
            url = "https://sample-videos.com/video123/mp4/720/big_buck_bunny.mp4",
            name = "Class 12th Hindi Padya Sahity Ka Vikas 50 MCQ Questions",
            totalTime = "59:00",
            description = "MCQ questions for Padya Sahity Ka Vikas for Hindi Class 12th Board Exams.",
            comments = listOf(
                Comment(user = "Sumit", text = "MCQs are very helpful."),
                Comment(user = "Shalini", text = "Thank you for uploading!")
            )
        )
    )


    fun getVideoById(id: String): Video? {
        return _videos.value.find { it.id == id }
    }


    fun retryPlayback(videoId: String, context: Context) {
        _playbackError.value = false
        _exoPlayer?.release()
        _exoPlayer = null
        getOrCreateExoPlayer(context, videoId) // reload video
    }

    fun getOrCreateExoPlayer(context: Context, videoId: String): ExoPlayer {
        if (_exoPlayer != null && _currentVideoId == videoId) {
            return _exoPlayer!!
        }

        _exoPlayer?.release()
        hasSoughtToSavedPosition = false

        _exoPlayer = ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(
                "https://content.jwplatform.com/manifests/vM7nH0Kl.m3u8"
            )
            setMediaItem(mediaItem)

            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    when (playbackState) {
                        Player.STATE_BUFFERING -> {
                            _isLoading.value = true
                        }
                        Player.STATE_READY -> {
                            if (_lastPosition.value > 0 && !hasSoughtToSavedPosition) {
                                seekTo(_lastPosition.value)
                                hasSoughtToSavedPosition = true
                            }
                            _isLoading.value = false
                        }
                        Player.STATE_ENDED -> {
                            _isLoading.value = false
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    _isLoading.value = false
                    _playbackError.value = true
                }
            })


            prepare()
            playWhenReady = true
        }

        _currentVideoId = videoId
        return _exoPlayer!!
    }

    fun releasePlayer() {
        _exoPlayer?.let { player ->
            if (_currentVideoId != null) saveProgress(_currentVideoId!!, player.currentPosition)
            player.release()
        }
        _exoPlayer = null
        _currentVideoId = null
    }
    fun loadProgress(videoId: String) {
        viewModelScope.launch {
            val savedProgress = repository.getProgress(videoId)
            val position = savedProgress?.lastPosition ?: 0L
            _lastPosition.value = position
            currentPlaybackPosition = position
        }
    }

    fun saveProgress(videoId: String, position: Long) {
        viewModelScope.launch {
            currentPlaybackPosition = position
            val videoProgress = VideoProgressEntity(
                videoId = videoId,
                lastPosition = position
            )
            repository.saveProgress(videoProgress)
            _lastPosition.value = position
        }
    }

    fun setFullscreen(isFullscreen: Boolean) {
        _isFullscreen.value = isFullscreen
    }

    fun toggleFullscreen() {
        _isFullscreen.value = !_isFullscreen.value
    }

    fun getCurrentPosition(): Long {
        return _exoPlayer?.currentPosition ?: currentPlaybackPosition
    }

    fun updateCurrentPosition(position: Long) {
        currentPlaybackPosition = position
    }

    override fun onCleared() {
        super.onCleared()
        _exoPlayer?.let { player ->
            if (_currentVideoId != null) {
                saveProgress(_currentVideoId!!, player.currentPosition)
            }
            player.release()
        }
    }
}