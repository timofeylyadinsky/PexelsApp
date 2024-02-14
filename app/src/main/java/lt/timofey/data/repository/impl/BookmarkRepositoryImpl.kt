package lt.timofey.data.repository.impl

import kotlinx.coroutines.flow.map
import lt.timofey.data.dao.BookmarkDao
import lt.timofey.data.entity.dbo.PhotosDbo
import lt.timofey.data.entity.dbo.toPhotosDto
import lt.timofey.data.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {
    override suspend fun savePhoto(photos: PhotosDbo) {
        bookmarkDao.savePhoto(photos)
    }

    override suspend fun getSavedPhotos() = bookmarkDao.selectBookmarkPhotos().map { it.toPhotosDto() }

    override suspend fun deletePhoto(photos: PhotosDbo) {
        TODO("Not yet implemented")
    }

    override suspend fun getSavedPhotoById(id: Int) {
        TODO("Not yet implemented")
    }

}