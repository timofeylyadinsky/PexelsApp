package lt.timofey.data.repository

import kotlinx.coroutines.flow.Flow
import lt.timofey.data.entity.dbo.PhotosDbo
import lt.timofey.data.entity.dto.PhotosDto
import lt.timofey.domain.entity.Photos

interface BookmarkRepository {
    suspend fun savePhoto(photos: PhotosDbo)
    suspend fun getSavedPhotos() : List<PhotosDto>
    suspend fun deletePhoto(photos: PhotosDbo)
    suspend fun getSavedPhotoById(id: Int) : PhotosDto?
}