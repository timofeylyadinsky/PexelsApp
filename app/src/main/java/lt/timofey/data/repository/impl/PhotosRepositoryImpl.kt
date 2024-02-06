package lt.timofey.data.repository.impl

import lt.timofey.data.api.ApiService
import lt.timofey.data.api.NetworkResult
import lt.timofey.data.entity.dto.CuratedPhotosDto
import lt.timofey.data.entity.dto.FeaturedCollectionsDto
import lt.timofey.data.entity.dto.SearchPhotosDto
import lt.timofey.data.repository.PhotosRepository
import lt.timofey.data.util.handleApi
import javax.inject.Inject

class PhotosRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
) : PhotosRepository  {
    override suspend fun getFeaturedCollections(): NetworkResult<FeaturedCollectionsDto> =
        handleApi { apiService.getFeaturedCollections() }

    override suspend fun getCuratedPhotos(): NetworkResult<CuratedPhotosDto> =
        handleApi { apiService.getCuratedPhotos() }

    override suspend fun searchForPhotos(searchText: String): NetworkResult<SearchPhotosDto> =
        handleApi { apiService.searchForPhotos(searchText) }
}