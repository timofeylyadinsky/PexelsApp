package lt.timofey.data.repository

import lt.timofey.data.api.NetworkResult
import lt.timofey.data.entity.dto.CuratedPhotosDto
import lt.timofey.data.entity.dto.FeaturedCollectionsDto
import lt.timofey.data.entity.dto.PhotosDto
import lt.timofey.data.entity.dto.SearchPhotosDto

interface PhotosRepository {
    suspend fun getFeaturedCollections(): NetworkResult<FeaturedCollectionsDto>
    suspend fun getCuratedPhotos(): NetworkResult<CuratedPhotosDto>
    suspend fun searchForPhotos(url: String): NetworkResult<SearchPhotosDto>

    suspend fun fetchPhotos(id: Int): NetworkResult<PhotosDto>
}