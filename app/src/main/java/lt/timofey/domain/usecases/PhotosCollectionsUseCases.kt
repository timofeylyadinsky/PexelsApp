package lt.timofey.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import lt.timofey.data.api.NetworkResult
import lt.timofey.data.repository.impl.PhotosRepositoryImpl
import lt.timofey.data.repository.module.IoDispatcher
import lt.timofey.domain.entity.CuratedPhotosData
import lt.timofey.domain.entity.FeaturedCollectionsData
import lt.timofey.domain.entity.PhotosData
import lt.timofey.domain.entity.SearchPhotosData
import lt.timofey.domain.entity.toCuratedPhotos
import lt.timofey.domain.entity.toFeaturedCollections
import lt.timofey.domain.entity.toPhotos
import lt.timofey.domain.entity.toSearchPhotos
import javax.inject.Inject

class getFeaturedCollectionsUseCase @Inject constructor(
    private val photosRepositoryImpl: PhotosRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<FeaturedCollectionsData> = withContext(ioDispatcher) {
        flow {
            when (val response = photosRepositoryImpl.getFeaturedCollections()) {
                is NetworkResult.Success -> emit(FeaturedCollectionsData(listOf(response.data.toFeaturedCollections())))
                is NetworkResult.Error -> emit(FeaturedCollectionsData(errorMessage = "${response.code} : ${response.message}"))
            }
        }
    }
}

class getCuratedPhotosUseCase @Inject constructor(
    private val photosRepositoryImpl: PhotosRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<CuratedPhotosData> = withContext(ioDispatcher) {
        flow {
            when (val response = photosRepositoryImpl.getCuratedPhotos()) {
                is NetworkResult.Success -> emit(CuratedPhotosData(listOf(response.data.toCuratedPhotos())))
                is NetworkResult.Error -> emit(CuratedPhotosData(errorMessage = "${response.code} : ${response.message}"))
            }
        }
    }
}

class getSearchPhotosUseCase @Inject constructor(
    private val photosRepositoryImpl: PhotosRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(searchQuery: String): Flow<SearchPhotosData> =
        withContext(ioDispatcher) {
            flow {
                when (val response = photosRepositoryImpl.searchForPhotos(searchQuery)) {
                    is NetworkResult.Success -> emit(SearchPhotosData(listOf(response.data.toSearchPhotos())))
                    is NetworkResult.Error -> emit(SearchPhotosData(errorMessage = "${response.code} : ${response.message}"))
                }
            }
        }
}

class fetchPhotoById @Inject constructor(
    private val photosRepositoryImpl: PhotosRepositoryImpl,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(id: Int) = withContext(ioDispatcher) {
        flow {
            when (val response = photosRepositoryImpl.fetchPhotos(id)) {
                is NetworkResult.Success -> emit(PhotosData(photos = response.data.toPhotos()))
                is NetworkResult.Error -> emit(PhotosData(errorMessage = "${response.code} : ${response.message}"))
            }
        }
    }
}