
import com.example.vidiyakul.data.dao.VideoProgressDao
import com.example.vidiyakul.data.model.VideoProgressEntity
import kotlinx.coroutines.flow.Flow


class VideoRepository(
    private val videoProgressDao: VideoProgressDao
) {
    suspend fun saveProgress(videoProgress: VideoProgressEntity) {
        videoProgressDao.insertOrUpdate(videoProgress)
    }

    suspend fun getProgress(videoId: String): VideoProgressEntity? {
        return videoProgressDao.getProgressByVideoId(videoId)
    }

    fun getProgressFlow(videoId: String): Flow<VideoProgressEntity?> {
        return videoProgressDao.getProgressFlow(videoId)
    }
}